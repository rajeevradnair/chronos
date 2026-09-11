data "aws_availability_zones" "available" {
  state = "available"
}

resource "aws_vpc" "chronos" {
  cidr_block           = "10.0.0.0/16"
  enable_dns_support   = true
  enable_dns_hostnames = true

  tags = {
    Name = "chronos-${var.environment}-vpc"
  }
}

resource "aws_internet_gateway" "chronos" {
  vpc_id = aws_vpc.chronos.id

  tags = {
    Name = "chronos-${var.environment}-igw"
  }
}

resource "aws_subnet" "public" {
  count = 2

  vpc_id                  = aws_vpc.chronos.id
  cidr_block              = cidrsubnet(aws_vpc.chronos.cidr_block, 8, count.index)
  availability_zone       = data.aws_availability_zones.available.names[count.index]
  map_public_ip_on_launch = true

  tags = {
    Name                     = "chronos-${var.environment}-public-${count.index + 1}"
    "kubernetes.io/role/elb" = "1"
  }
}

resource "aws_subnet" "private" {
  count = 2

  vpc_id            = aws_vpc.chronos.id
  cidr_block        = cidrsubnet(aws_vpc.chronos.cidr_block, 8, count.index + 10)
  availability_zone = data.aws_availability_zones.available.names[count.index]

  tags = {
    Name                              = "chronos-${var.environment}-private-${count.index + 1}"
    "kubernetes.io/role/internal-elb" = "1"
  }
}

resource "aws_eip" "nat" {
  domain = "vpc"

  tags = {
    Name = "chronos-${var.environment}-nat-eip"
  }
}

resource "aws_nat_gateway" "chronos" {
  allocation_id = aws_eip.nat.id
  subnet_id     = aws_subnet.public[0].id

  depends_on = [aws_internet_gateway.chronos]

  tags = {
    Name = "chronos-${var.environment}-nat"
  }
}

resource "aws_route_table" "public" {
  vpc_id = aws_vpc.chronos.id

  route {
    cidr_block = "0.0.0.0/0"
    gateway_id = aws_internet_gateway.chronos.id
  }

  tags = {
    Name = "chronos-${var.environment}-public-rt"
  }
}

resource "aws_route_table_association" "public" {
  count = 2

  subnet_id      = aws_subnet.public[count.index].id
  route_table_id = aws_route_table.public.id
}

resource "aws_route_table" "private" {
  vpc_id = aws_vpc.chronos.id

  route {
    cidr_block     = "0.0.0.0/0"
    nat_gateway_id = aws_nat_gateway.chronos.id
  }

  tags = {
    Name = "chronos-${var.environment}-private-rt"
  }
}

resource "aws_route_table_association" "private" {
  count = 2

  subnet_id      = aws_subnet.private[count.index].id
  route_table_id = aws_route_table.private.id
}


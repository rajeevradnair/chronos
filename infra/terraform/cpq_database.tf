resource "aws_db_subnet_group" "cpq" {
  name       = "chronos-${var.environment}-cpq"
  subnet_ids = aws_subnet.private[*].id

  tags = {
    Name = "chronos-${var.environment}-cpq-db-subnets"
  }
}

resource "aws_security_group" "cpq_database" {
  name        = "chronos-${var.environment}-cpq-database"
  description = "Allow CPQ database access from EKS"
  vpc_id      = aws_vpc.chronos.id

  ingress {
    description = "PostgreSQL from EKS"
    from_port   = 5432
    to_port     = 5432
    protocol    = "tcp"

    security_groups = [
      aws_eks_cluster.chronos.vpc_config[0].cluster_security_group_id
    ]
  }

  tags = {
    Name = "chronos-${var.environment}-cpq-database"
  }
}

resource "aws_db_instance" "cpq" {
  identifier = "chronos-${var.environment}-cpq"

  engine         = "postgres"
  instance_class = "db.t4g.micro"

  allocated_storage     = 20
  max_allocated_storage = 50
  storage_type          = "gp3"
  storage_encrypted     = true

  db_name  = "chronos_cpq"
  username = "chronos"
  password = var.cpq_db_password
  port     = 5432

  db_subnet_group_name   = aws_db_subnet_group.cpq.name
  vpc_security_group_ids = [aws_security_group.cpq_database.id]

  publicly_accessible = false

  multi_az            = false
  skip_final_snapshot = true

  tags = {
    Name    = "chronos-${var.environment}-cpq"
    Service = "cpq"
  }
}
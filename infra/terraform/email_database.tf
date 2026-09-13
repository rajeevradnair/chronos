resource "aws_db_subnet_group" "email" {
  name       = "chronos-${var.environment}-email"
  subnet_ids = aws_subnet.private[*].id

  tags = {
    Name = "chronos-${var.environment}-email-db-subnets"
  }
}

resource "aws_security_group" "email_database" {
  name        = "chronos-${var.environment}-email-database"
  description = "Allow Email database access from EKS"
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
    Name = "chronos-${var.environment}-email-database"
  }
}

resource "aws_db_instance" "email" {
  identifier = "chronos-${var.environment}-email"

  engine         = "postgres"
  instance_class = "db.t4g.micro"

  allocated_storage     = 20
  max_allocated_storage = 50
  storage_type          = "gp3"
  storage_encrypted     = true

  db_name  = "chronos_email"
  username = "chronos"
  password = var.email_db_password
  port     = 5432

  db_subnet_group_name   = aws_db_subnet_group.email.name
  vpc_security_group_ids = [aws_security_group.email_database.id]

  publicly_accessible = false

  multi_az            = false
  skip_final_snapshot = true

  tags = {
    Name    = "chronos-${var.environment}-email"
    Service = "email"
  }
}
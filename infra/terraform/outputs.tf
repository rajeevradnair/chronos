output "aws_region" {
  description = "AWS region used by Chronos"
  value       = var.aws_region
}

output "environment" {
  description = "Chronos deployment environment"
  value       = var.environment
}

output "vpc_id" {
  description = "Chronos VPC ID"
  value       = aws_vpc.chronos.id
}

output "public_subnet_ids" {
  description = "Chronos public subnet IDs"
  value       = aws_subnet.public[*].id
}

output "private_subnet_ids" {
  description = "Chronos private subnet IDs"
  value       = aws_subnet.private[*].id
}

output "ecr_repository_url" {
  description = "Chronos ECR repository URL"
  value       = aws_ecr_repository.chronos.repository_url
}

output "eks_cluster_name" {
  value = aws_eks_cluster.chronos.name
}

output "eks_cluster_endpoint" {
  value = aws_eks_cluster.chronos.endpoint
}

output "eks_oidc_provider_arn" {
  value = aws_iam_openid_connect_provider.eks.arn
}

output "crm_db_endpoint" {
  description = "CRM PostgreSQL endpoint"
  value       = aws_db_instance.crm.address
}

output "crm_db_port" {
  description = "CRM PostgreSQL port"
  value       = aws_db_instance.crm.port
}

output "crm_db_name" {
  description = "CRM PostgreSQL database name"
  value       = aws_db_instance.crm.db_name
}

output "cpq_db_endpoint" {
  description = "CPQ PostgreSQL endpoint"
  value       = aws_db_instance.cpq.address
}

output "cpq_db_port" {
  description = "CPQ PostgreSQL port"
  value       = aws_db_instance.cpq.port
}

output "cpq_db_name" {
  description = "CPQ PostgreSQL database name"
  value       = aws_db_instance.cpq.db_name
}
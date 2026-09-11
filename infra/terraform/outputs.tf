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
data "aws_caller_identity" "current" {}

output "aws_account_id" {
  description = "AWS account hosting Chronos"
  value       = data.aws_caller_identity.current.account_id
}
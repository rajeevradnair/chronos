variable "aws_region" {
  description = "AWS region for Chronos resources"
  type        = string
  default     = "us-west-2"
}

variable "environment" {
  description = "Chronos deployment environment"
  type        = string
  default     = "dev"
}
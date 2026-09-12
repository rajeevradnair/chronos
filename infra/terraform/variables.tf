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

variable "crm_db_password" {
  description = "Master password for the CRM PostgreSQL database"
  type        = string
  sensitive   = true
}

variable "cpq_db_password" {
  description = "Master password for the CPQ PostgreSQL database"
  type        = string
  sensitive   = true
}


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

variable "conversation_db_password" {
  type      = string
  sensitive = true
}

variable "knowledge_db_password" {
  type      = string
  sensitive = true
}

variable "support_db_password" {
  description = "Master password for the Support PostgreSQL database"
  type        = string
  sensitive   = true
}

variable "email_db_password" {
  description = "Master password for the Email PostgreSQL database"
  type        = string
  sensitive   = true
}
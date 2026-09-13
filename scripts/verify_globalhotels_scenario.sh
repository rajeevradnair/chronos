#!/usr/bin/env bash

set -euo pipefail

echo "=== GlobalHotels enterprise scenario verification ==="

SERVICES=(
  crm
  cpq
  conversation
  knowledge
  support
  email
)

for service in "${SERVICES[@]}"; do
  echo
  echo "Verifying $service..."

  (
    cd "services/$service"
    mvn -q test
  )

  echo "$service: PASS"
done

echo
echo "=== Scenario source systems ==="
echo "CRM          : ACC-1001 / OPP-812"
echo "CPQ          : QUOTE-V1 -> QUOTE-V2 -> QUOTE-V3 / DISCOUNT-17 / APPROVAL-88"
echo "Conversation : CALL-20 / CALL-44"
echo "Knowledge    : POLICY-V1 / POLICY-V2 / product docs / battlecard / playbook"
echo "Support      : SUPPORT-91"
echo "Email        : THREAD-103 / EMAIL-103"

echo
echo "GLOBALHOTELS SCENARIO: PASS"
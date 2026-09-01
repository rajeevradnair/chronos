import unittest

from pydantic import ValidationError

from evidence.models import EvidenceRecord
from evidence_fusion.fixtures import (
    GLOBALHOTELS_DECISION_FACTS,
    GLOBALHOTELS_EVIDENCE,
)
from evidence_fusion.fusion import (
    build_evidence_bundle,
    deduplicate_evidence,
)


QUESTION = "Why did we approve 17% for GlobalHotels?"


class EvidenceFusionTests(unittest.TestCase):

    def test_chunk_id_may_be_null_for_document_evidence(self):
        policy = next(
            record
            for record in GLOBALHOTELS_EVIDENCE
            if record.evidence_id == "POLICY-V2"
        )

        self.assertIsNone(policy.chunk_id)
        self.assertEqual("POLICY-V2", policy.document_id)

    def test_duplicate_evidence_is_removed(self):
        result = deduplicate_evidence(GLOBALHOTELS_EVIDENCE)

        self.assertEqual(3, len(result))

        self.assertEqual(
            ["CALL-44", "EMAIL-103", "POLICY-V2"],
            [record.evidence_id for record in result],
        )

    def test_bundle_preserves_decision_facts(self):
        bundle = build_evidence_bundle(
            question=QUESTION,
            decision_facts=GLOBALHOTELS_DECISION_FACTS,
            evidence=GLOBALHOTELS_EVIDENCE,
        )

        self.assertEqual(4, len(bundle.decision_facts))

        facts = {
            fact.name: fact.value
            for fact in bundle.decision_facts
        }

        self.assertEqual(3_800_000, facts["opportunity_value"])
        self.assertEqual("v3", facts["quote_version"])
        self.assertEqual(17, facts["discount_percent"])
        self.assertEqual("approved", facts["approval_status"])

    def test_fusion_preserves_complete_evidence_metadata(self):
        bundle = build_evidence_bundle(
            question=QUESTION,
            decision_facts=GLOBALHOTELS_DECISION_FACTS,
            evidence=GLOBALHOTELS_EVIDENCE,
        )

        call = next(
            record
            for record in bundle.evidence
            if record.evidence_id == "CALL-44"
        )

        self.assertEqual("CALL-44-CHUNK-1", call.chunk_id)

        self.assertEqual("conversation", call.source_system)
        self.assertEqual("transcript", call.source_entity_type)
        self.assertEqual("CALL-44", call.source_entity_id)

        self.assertEqual("ACC-1001", call.account_id)
        self.assertEqual("OPP-812", call.opportunity_id)

        self.assertEqual("customer_statement", call.authority)

        self.assertEqual(
            "conversation",
            call.provenance.source_system,
        )
        self.assertEqual(
            "CALL-44",
            call.provenance.source_entity_id,
        )

        self.assertIsNotNone(call.valid_from)
        self.assertIsNotNone(call.system_from)

        self.assertEqual(
            "confidential",
            call.permissions.classification,
        )
        self.assertIn(
            "sales",
            call.permissions.allowed_roles,
        )

        self.assertEqual(
            "hash-call-44-chunk-1",
            call.content_hash,
        )

        self.assertEqual(
            "fixture-embedding",
            call.embedding_metadata.model,
        )
        self.assertEqual(
            "1",
            call.embedding_metadata.version,
        )
        self.assertEqual(
            3,
            call.embedding_metadata.dimensions,
        )

    def test_evidence_record_rejects_extra_fields(self):
        valid = GLOBALHOTELS_EVIDENCE[0].model_dump()

        valid["invented_field"] = "should-not-exist"

        with self.assertRaises(ValidationError):
            EvidenceRecord.model_validate(valid)


if __name__ == "__main__":
    unittest.main()
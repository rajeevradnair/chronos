import unittest

from historical_reconstruction.fixtures import GLOBALHOTELS_EVIDENCE, utc
from historical_reconstruction.reconstruct import reconstruct_as_of


class HistoricalReconstructionTests(unittest.TestCase):

    def evidence_ids_at(self, timestamp: str) -> set[str]:
        result = reconstruct_as_of(
            GLOBALHOTELS_EVIDENCE,
            utc(timestamp),
        )
        return {item["evidence_id"] for item in result}

    def test_leakage_1_policy_cannot_appear_before_effective_date(self):
        ids = self.evidence_ids_at("2026-02-10T12:00:00")

        self.assertIn("CALL-44", ids)
        self.assertNotIn("POLICY-V2", ids)

    def test_leakage_2_technical_limitation_cannot_appear_before_discovery(self):
        ids = self.evidence_ids_at("2026-02-15T12:00:00")

        self.assertIn("POLICY-V2", ids)
        self.assertNotIn("TECH-LIMITATION-1", ids)

    def test_leakage_3_july_capability_cannot_contaminate_february(self):
        ids = self.evidence_ids_at("2026-02-28T12:00:00")

        self.assertIn("TECH-LIMITATION-1", ids)
        self.assertNotIn("CAPABILITY-RELEASE-1", ids)

    def test_half_open_boundary_replaces_old_evidence(self):
        ids = self.evidence_ids_at("2026-07-10T00:00:00")

        self.assertNotIn("TECH-LIMITATION-1", ids)
        self.assertIn("CAPABILITY-RELEASE-1", ids)


if __name__ == "__main__":
    unittest.main()
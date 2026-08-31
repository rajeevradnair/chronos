import unittest

from historical_reconstruction.bitemporal_examples import (
    evaluate_corrected_knowledge_truth_table,
    evaluate_late_arriving_policy_truth_table,
)


class BitemporalReconstructionTests(unittest.TestCase):

    def test_late_arriving_policy_truth_table(self):
        results = evaluate_late_arriving_policy_truth_table()

        for result in results:
            with self.subTest(case=result["case"]):
                self.assertEqual(
                    result["expected"],
                    result["actual"],
                )

    def test_corrected_knowledge_truth_table(self):
        results = evaluate_corrected_knowledge_truth_table()

        for result in results:
            with self.subTest(case=result["case"]):
                self.assertEqual(
                    [result["expected_evidence_id"]],
                    result["actual_evidence_ids"],
                )


if __name__ == "__main__":
    unittest.main()
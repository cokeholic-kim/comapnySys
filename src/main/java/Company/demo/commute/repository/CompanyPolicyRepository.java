package Company.demo.commute.repository;

import Company.demo.commute.dao.CompanyPolicy;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyPolicyRepository extends JpaRepository<CompanyPolicy,Long> {
    Optional<CompanyPolicy> findByPolicyGubnAndPolicyName(String policyGubn, String policyName);
}

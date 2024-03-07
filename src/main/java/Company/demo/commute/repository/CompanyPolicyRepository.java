package Company.demo.commute.repository;

import Company.demo.commute.dao.CompanyPolicy;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyPolicyRepository extends JpaRepository<CompanyPolicy,Long> {
    CompanyPolicy findByPolicyGubnAndPolicyName(String policyGubn,String policyName);
}

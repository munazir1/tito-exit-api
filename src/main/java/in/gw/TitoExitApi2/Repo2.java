package in.gw.TitoExitApi2;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface Repo2 extends JpaRepository<TitoDetails, String> {
	
	
	@Query(value = ";WITH LocationFiltered AS ( SELECT GateSlip, LocationCode, TimeStamp, Processed, ROW_NUMBER() OVER (PARTITION BY GateSlip, LocationCode ORDER BY TimeStamp) AS RowNum FROM [TITO_Details] WHERE Processed = 0 AND (LocationCode = 'GATE_3' OR LocationCode = 'GATE_1' OR LocationCode = 'GATE_2' or LocationCode='GATE_4' ) ) SELECT * FROM LocationFiltered WHERE RowNum = 2;", nativeQuery = true)
public List<TitoDetails> findGateSlipTimes();



@Modifying
@Transactional
@Query(value="UPDATE Tito_Details SET Processed = 1  WHERE gateSlip = :gateSlip and Processed=0",nativeQuery = true)
  public void updateTitoDetailsProcessedStatus(@Param("gateSlip") String gateSlip);

}
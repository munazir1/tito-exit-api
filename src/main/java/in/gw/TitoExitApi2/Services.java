package in.gw.TitoExitApi2;



import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;





@Service
public class Services {
	
	@Autowired
	public Repo repo;
    
	@Autowired
	public Repo2 repo2;

	public void  savedata(TitoData en) {
	repo.save(en);
	}
	
    public List<TitoData> gettito(){
		List<TitoData> all = repo.findAll();
return all;
    }
	
	  public List<TitoDetails> getdata()
	  { List<TitoDetails> gateSlipTimes = repo2.findGateSlipTimes(); return gateSlipTimes; }
	 
	   public List<TitoData> getAllTitoDetails(){
			List<TitoData> all = repo.findAll();
	return all;
	    }
		public List<TitoDetails> getExistLocationtime(){
		List<TitoDetails> gateSlipTimes = repo2. findGateSlipTimes();
		for (TitoDetails titoDetails : gateSlipTimes) {
			Timestamp timeStamp = titoDetails.getTimeStamp();
			System.out.println("timeStamp"+timeStamp);
		}
		return gateSlipTimes;
		}
		 public TitoDetails saveTitoDetails(TitoDetails titoDetails) {
		        return repo2.save(titoDetails);
		    }
		 public void updateProcessedStatus(String gateSlip) {
			 System.out.println("update--------------------------");
		        repo2.updateTitoDetailsProcessedStatus(gateSlip);
		    }
	 
}

package in.gw.TitoExitApi2;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface Repo extends JpaRepository<TitoData,String> {

	
	
}


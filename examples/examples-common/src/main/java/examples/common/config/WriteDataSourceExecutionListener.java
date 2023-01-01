package examples.common.config;

import java.util.List;
import net.ttddyy.dsproxy.listener.QueryExecutionListener;

public interface WriteDataSourceExecutionListener {

  List<QueryExecutionListener> getListeners();

}

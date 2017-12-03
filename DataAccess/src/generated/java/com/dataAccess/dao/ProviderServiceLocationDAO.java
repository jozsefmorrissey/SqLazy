package com.dataAccess.dao;

import com.dataAccess.bean.ProviderServiceLocation;
import java.lang.String;
import java.util.List;

public interface ProviderServiceLocationDAO {
  List<ProviderServiceLocation> getNonSense(String partyPooper, String billVermin);

  List<ProviderServiceLocation> getEverything(String breadButter);
}

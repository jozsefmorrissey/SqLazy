package com.dataAccess.dao;

import com.dataAccess.bean.user_info;
import java.lang.String;
import java.util.List;

public interface user_infoDAO {
  List<user_info> getUserByEmail(String email);
}

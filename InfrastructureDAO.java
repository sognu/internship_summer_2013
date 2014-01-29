package com.williams.infrastructureBiz.dao;

import java.util.ArrayList;
import java.util.List;


public class InfrastructureDAO extends PassageClientDaoSupport {

  
  /*Get the business types for drop down.*/
     public List<String> getBusType() {
        return (List<String>) getSqlMapClientTemplate().queryForList("infrastructureBiz.getBusType");
    }

  /*Save company informationn.*/
  public void saveBARequestEditCompany(BARequestCompanyInfoDO company){
      getSqlMapClientTemplate().delete("infrastructureBiz.deleteBARequestEditCompany", company);
      getSqlMapClientTemplate().insert("infrastructureBiz.saveBARequestEditCompany", company);
  }

  /*Save user information.*/
  public void saveBARequestEditUsers(List<BARequestUserInfoDO> users){
      for(BARequestUserInfoDO user: users){
      getSqlMapClientTemplate().delete("infrastructureBiz.deleteBARequestEditUsers", user);
      getSqlMapClientTemplate().insert("infrastructureBiz.saveBARequestEditUsers", user);
      }
  }

  /*Generate a login id for each user.*/
   public String generateLoginId(String firstName, String lastName) throws DataAccessException {
          if ( firstName == null ) firstName = "";
        if ( lastName == null ) lastName = "";
        if ( firstName.length() > 0 ) firstName = firstName.substring(0,1);
        if ( lastName.length() > 4 ) lastName = lastName.substring(0,5);
        String loginId = firstName + lastName;
        UserDO user = new UserDO();
        user.setLoginId(loginId);
        int count = 0;
        while ( true ) {
            count++;
            List<UserDO> exist = getSqlMapClientTemplate().queryForList("infrastructureBiz.selectUser", user);
            //Have to check both place since user_logon_id may be in sys_user or sys_user_rqst
            List<UserDO> existBARqst = getSqlMapClientTemplate().queryForList("infrastructureBiz.selectUserBARqst", user);
            if ( exist.size() > 0 || existBARqst.size() > 0) {
                user.setLoginId(loginId+count);
            }
            else {
                return user.getLoginId();
            }
        }
    }

    /* Submit the editing session */
    public void submitBARequestEdit(BARequestCompanyInfoDO company){
      getSqlMapClientTemplate().update("infrastructureBiz.submitBaRequestEdit", company);
    }
 
}

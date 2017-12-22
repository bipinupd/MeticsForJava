package com.bu.controller.customer;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;
import javax.inject.Singleton;

import com.bu.entities.WebUser;
import com.bu.util.JsonUtil;
import com.jayway.jsonpath.DocumentContext;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.ParseException;

@Named("jsonAndObjectMapper")
@Singleton
public class JsonAndObjectMapper {

  public WebUser mapJsonToUser(String json) {
    return mapJsonToUser(null, json);
  }
  
  public String mapUserToJson(WebUser User, String requestUrl) {
    return constructJsonUser(requestUrl, User).toString();
  }

  public String mapUserToJsonWithoutId(WebUser User, String requestUrl) {
    return constructJsonUserWithoutId(requestUrl, User).toString();
  }

  public String mapUsersToJson(List<WebUser> Users, String requestUrl) {
    JSONObject json = new JSONObject();
    JSONArray jsonUsers = new JSONArray();
    for (WebUser User : Users) {
      jsonUsers.add(constructJsonUser(requestUrl, User));
    }
    json.put("Users", jsonUsers);
    return json.toString();
  }

  private JSONObject constructJsonUserWithoutId(String requestUrl, WebUser User) {
    JSONObject json = new JSONObject();
    json.put("links", JsonUtil.createSelfUrl(requestUrl, User.getId()));
    json.put("firstName", User.getFirstName());
    json.put("lastName", User.getLastName());
    return json;
  }

  private JSONObject constructJsonUser(String requestUrl, WebUser User) {
    JSONObject json = constructJsonUserWithoutId(requestUrl, User);
    json.put("id", User.getId());
    return json;
  }

  public List<WebUser> mapJsonToUsers(String json) throws ParseException {
    List<WebUser> Users = new ArrayList<>();
    DocumentContext jsonContext = JsonUtil.getJsonContext(json);
    JSONArray jsonUsers = jsonContext.read("$.Users");
    for (int i = 0; i < jsonUsers.size(); i++) {
      Number id = jsonContext.read("$.Users[" + i + "].id");
      String firstName = jsonContext.read("$.Users[" + i + "].firstName");
      String lastName = jsonContext.read("$.Users[" + i + "].lastName");
      Users.add(new WebUser(id.longValue(), firstName, lastName));
    }
    return Users;
  }

  public WebUser mapJsonToUser(Long id, String json) {
    DocumentContext jsonContext = JsonUtil.getJsonContext(json);
    Number jsonId = jsonContext.read("$.id");
    String firstName = jsonContext.read("$.firstName");
    String lastName = jsonContext.read("$.lastName");
    if (jsonId != null) {
      return new WebUser(jsonId.longValue(), firstName, lastName);
    }
    if (id == null) {
      return new WebUser(firstName, lastName);
    }
    return new WebUser(id, firstName, lastName);
  }
}

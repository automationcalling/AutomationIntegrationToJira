package com.automationcalling.utils.jira;

import com.automationcalling.utils.common.CommonMethods;
import com.automationcalling.utils.common.Constant;
import com.automationcalling.utils.restassured.RestAssuredCore;

import static com.automationcalling.utils.common.Constant.PAYLOADPATH;

public class JiraUtil {
    RestAssuredCore restAssuredCore;

    public JiraUtil() {
        try {
            restAssuredCore = new RestAssuredCore(CommonMethods.returnProperties(Constant.PROPRETYFILEPATH, "jiraBASEURI")
                    , CommonMethods.returnProperties(Constant.PROPRETYFILEPATH, "jiraBASEPATH"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String returnSessionID() {
        return restAssuredCore.setURLEncodingStatus(false)
                .setContentType("application/json")
                .setBody(PAYLOADPATH + "Credential.json")
                .invokeRestCall("POST", "/auth/1/session").getJsonPathReturnValue("str", "session.value");
    }


    public String createBug(String... jiraValues) {
        return restAssuredCore.setURLEncodingStatus(false)
                .setContentType("application/json")
                .setHeader("Cookie", "JSESSIONID=" + returnSessionID())
                .setBodyasString("{\n" +
                        "    \"fields\": {\n" +
                        "       \"project\":\n" +
                        "       { \n" +
                        "          \"key\": \"" + jiraValues[0] + "\"\n" +
                        "       },\n" +
                        "       \"summary\": \"" + jiraValues[1] + "\",\n" +
                        "       \"description\": \"" + jiraValues[2] + "\",\n" +
                        "       \"issuetype\": {\n" +
                        "          \"name\": \"Bug\"\n" +
                        "       }\n" +
                        "   }\n" +
                        "}")
                .invokeRestCall("POST", "/api/2/issue").getJsonPathReturnValue("in", "id");
    }


    /* The TransitionID keep changes based on flow of JIRA
    The ID remain constant from one flow to another flow for eg., Done to Reopen remain same but Reopen to In-progress has different id
    To check the status http://localhost:8080/rest/api/2/issue/YOURISSUENO/transitions
     */

    public int changeJiraWorkFlowStatus(String ticketNo, String comments, String transitionID) {
        return restAssuredCore.setURLEncodingStatus(false)
                .setContentType("application/json")
                .setHeader("Cookie", "JSESSIONID=" + returnSessionID())
                .setBodyasString("{\n" +
                        "    \"update\": {\n" +
                        "        \"comment\": [\n" +
                        "            {\n" +
                        "                \"add\": {\n" +
                        "                    \"body\": \"" + comments + "\"\n" +
                        "                }\n" +
                        "            }\n" +
                        "        ]\n" +
                        "    },\n" +
                        "    \"transition\": {\n" +
                        "        \"id\": \"" + transitionID + "\"\n" +
                        "    }\n" +
                        "}")
                .invokeRestCall("POST", "/api/latest/issue/" + ticketNo + "/transitions?expand=transitions.fields").getStatusCode();
    }
}
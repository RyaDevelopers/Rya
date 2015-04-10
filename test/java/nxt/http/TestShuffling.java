package nxt.http;

import nxt.BlockchainTest;
import nxt.Constants;
import nxt.Shuffling;
import nxt.util.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class TestShuffling extends BlockchainTest {

    @Test
    public void shufflingProcess() {
        String shufflingId = create();
        register(shufflingId);
        process(shufflingId);
        verify(shufflingId);
        distribute(shufflingId);
        tryCancel(shufflingId);

        // Verify that only fees were reduced
        Assert.assertEquals(-4 * Constants.ONE_NXT, testers.get(1).getBalanceDiff());
        Assert.assertEquals(-4 * Constants.ONE_NXT, testers.get(1).getUnconfirmedBalanceDiff());
        Assert.assertEquals(-3 * Constants.ONE_NXT, testers.get(2).getBalanceDiff());
        Assert.assertEquals(-3 * Constants.ONE_NXT, testers.get(2).getUnconfirmedBalanceDiff());
        Assert.assertEquals(-3 * Constants.ONE_NXT, testers.get(3).getBalanceDiff());
        Assert.assertEquals(-3 * Constants.ONE_NXT, testers.get(3).getUnconfirmedBalanceDiff());
        Assert.assertEquals(-3 * Constants.ONE_NXT, testers.get(4).getBalanceDiff());
        Assert.assertEquals(-3 * Constants.ONE_NXT, testers.get(4).getUnconfirmedBalanceDiff());
    }

    private String create() {
        APICall apiCall = new APICall.Builder("shufflingCreate").
                secretPhrase(secretPhrase1).
                feeNQT(Constants.ONE_NXT).
                param("amountNQT", "10000000").
                param("participantCount", "4").
                param("cancellationHeight", Integer.MAX_VALUE).
                build();
        JSONObject response = apiCall.invoke();
        Logger.logMessage("shufflingCreateResponse: " + response.toJSONString());
        generateBlock();
        return (String) response.get("transaction");
    }

    private String register(String shufflingId) {
        APICall apiCall = new APICall.Builder("shufflingRegister").
                secretPhrase(secretPhrase2).
                feeNQT(Constants.ONE_NXT).
                param("shuffling", shufflingId).
                build();
        JSONObject response = apiCall.invoke();
        Logger.logMessage("shufflingRegisterResponse: " + response.toJSONString());
        generateBlock();
        apiCall = new APICall.Builder("shufflingRegister").
                secretPhrase(secretPhrase3).
                feeNQT(Constants.ONE_NXT).
                param("shuffling", shufflingId).
                build();
        response = apiCall.invoke();
        Logger.logMessage("shufflingRegisterResponse: " + response.toJSONString());
        generateBlock();
        apiCall = new APICall.Builder("shufflingRegister").
                secretPhrase(secretPhrase4).
                feeNQT(Constants.ONE_NXT).
                param("shuffling", shufflingId).
                build();
        response = apiCall.invoke();
        Logger.logMessage("shufflingRegisterResponse: " + response.toJSONString());
        generateBlock();
        apiCall = new APICall.Builder("getShuffling").
                param("shuffling", shufflingId).
                build();
        JSONObject getShufflingResponse = apiCall.invoke();
        Logger.logMessage("getShufflingResponse: " + getShufflingResponse.toJSONString());

        apiCall = new APICall.Builder("getShufflingParticipants").
                param("shuffling", shufflingId).
                build();
        JSONObject getParticipantsResponse = apiCall.invoke();
        Logger.logMessage("getShufflingParticipantsResponse: " + getParticipantsResponse.toJSONString());

        Assert.assertEquals((long) Shuffling.Stage.PROCESSING.getCode(), getShufflingResponse.get("stage"));
        String shufflingAssignee = (String) getShufflingResponse.get("assignee");
        JSONArray participants = (JSONArray)getParticipantsResponse.get("participants");
        Map<String, String> accountMapping = new HashMap<>();
        for (Object participant : participants) {
            String account = (String) ((JSONObject)participant).get("account");
            String nextAccount = (String) ((JSONObject)participant).get("nextAccount");
            accountMapping.put(account, nextAccount);
        }
        String account1 = accountMapping.get(shufflingAssignee);
        Assert.assertTrue(account1 != null);
        String account2 = accountMapping.get(account1);
        Assert.assertTrue(account2 != null);
        String account3 = accountMapping.get(account2);
        Assert.assertTrue(account3 != null);
        String account4 = accountMapping.get(account3);
        Assert.assertTrue(account4 != null);
        String nullAccount = accountMapping.get(account4);
        Assert.assertTrue(nullAccount == null);
        return shufflingId;
    }

    private void process(String shufflingId) {
        APICall apiCall = new APICall.Builder("shufflingProcess").
                param("shuffling", shufflingId).
                param("secretPhrase", secretPhrase1).
                param("recipient", testers.get(1).getStrId()).
                feeNQT(Constants.ONE_NXT).
                build();
        JSONObject shufflingProcessResponse = apiCall.invoke();
        Logger.logMessage("shufflingProcessResponse: " + shufflingProcessResponse.toJSONString());
        generateBlock();

        apiCall = new APICall.Builder("shufflingProcess").
                param("shuffling", shufflingId).
                param("secretPhrase", secretPhrase2).
                param("recipient", testers.get(2).getStrId()).
                feeNQT(Constants.ONE_NXT).
                build();
        shufflingProcessResponse = apiCall.invoke();
        Logger.logMessage("shufflingProcessResponse: " + shufflingProcessResponse.toJSONString());
        generateBlock();

        apiCall = new APICall.Builder("shufflingProcess").
                param("shuffling", shufflingId).
                param("secretPhrase", secretPhrase3).
                param("recipient", testers.get(3).getStrId()).
                feeNQT(Constants.ONE_NXT).
                build();
        shufflingProcessResponse = apiCall.invoke();
        Logger.logMessage("shufflingProcessResponse: " + shufflingProcessResponse.toJSONString());
        generateBlock();

        apiCall = new APICall.Builder("shufflingProcess").
                param("shuffling", shufflingId).
                param("secretPhrase", secretPhrase4).
                param("recipient", testers.get(4).getStrId()).
                feeNQT(Constants.ONE_NXT).
                build();
        shufflingProcessResponse = apiCall.invoke();
        Logger.logMessage("shufflingProcessResponse: " + shufflingProcessResponse.toJSONString());
        generateBlock();

        // Verify that each of the participants is also a recipient (not mandatory just for the test)
        apiCall = new APICall.Builder("getShufflingParticipants").
                param("shuffling", shufflingId).
                build();
        JSONObject getParticipantsResponse = apiCall.invoke();
        Logger.logMessage("getShufflingParticipantsResponse: " + getParticipantsResponse.toJSONString());

        JSONArray participants = (JSONArray)getParticipantsResponse.get("participants");
        Map<String, String> accountMapping = new HashMap<>();
        for (Object participant : participants) {
            String account = (String) ((JSONObject)participant).get("account");
            String recipient = (String) ((JSONObject)participant).get("recipient");
            accountMapping.put(account, recipient);
        }
        for (Map.Entry<String, String> mapping : accountMapping.entrySet()) {
            Assert.assertTrue(accountMapping.get(mapping.getValue()) != null);
            Logger.logMessage(String.format("account %s mapped to account %s", mapping.getValue(), accountMapping.get(mapping.getValue())));
        }
    }

    private void verify(String shufflingId) {
        APICall apiCall = new APICall.Builder("shufflingVerify").
                param("shuffling", shufflingId).
                param("secretPhrase", secretPhrase1).
                feeNQT(Constants.ONE_NXT).
                build();
        apiCall.invoke();
        apiCall = new APICall.Builder("shufflingVerify").
                param("shuffling", shufflingId).
                param("secretPhrase", secretPhrase2).
                feeNQT(Constants.ONE_NXT).
                build();
        apiCall.invoke();
        apiCall = new APICall.Builder("shufflingVerify").
                param("shuffling", shufflingId).
                param("secretPhrase", secretPhrase3).
                feeNQT(Constants.ONE_NXT).
                build();
        apiCall.invoke();
        apiCall = new APICall.Builder("shufflingVerify").
                param("shuffling", shufflingId).
                param("secretPhrase", secretPhrase4).
                feeNQT(Constants.ONE_NXT).
                build();
        JSONObject response = apiCall.invoke();
        Logger.logDebugMessage("shufflingVerifyResponse:" + response);
    }

    private void distribute(String shufflingId) {
        APICall apiCall = new APICall.Builder("shufflingDistribute").
                param("shuffling", shufflingId).
                param("secretPhrase", secretPhrase1).
                feeNQT(Constants.ONE_NXT).
                build();
        JSONObject response = apiCall.invoke();
        Logger.logDebugMessage("shufflingDistributeResponse:" + response);
        Assert.assertTrue(((String) response.get("error")).contains("Shuffling not ready for distribution"));
        generateBlock();
        apiCall = new APICall.Builder("shufflingDistribute").
                param("shuffling", shufflingId).
                param("secretPhrase", secretPhrase4).
                feeNQT(Constants.ONE_NXT).
                build();
        response = apiCall.invoke();
        Logger.logDebugMessage("shufflingDistributeResponse:" + response);
        Assert.assertTrue(((String)response.get("error")).contains("Only shuffling issuer"));
        apiCall = new APICall.Builder("shufflingDistribute").
                param("shuffling", shufflingId).
                param("secretPhrase", secretPhrase1).
                feeNQT(Constants.ONE_NXT).
                build();
        response = apiCall.invoke();
        Logger.logDebugMessage("shufflingDistributeResponse:" + response);

        // Duplicate transaction in the same block
        apiCall = new APICall.Builder("shufflingDistribute").
                param("shuffling", shufflingId).
                param("secretPhrase", secretPhrase1).
                feeNQT(Constants.ONE_NXT).
                build();
        response = apiCall.invoke();
        Logger.logDebugMessage("shufflingDistributeResponse:" + response);
        generateBlock();

        apiCall = new APICall.Builder("getShuffling").
                param("shuffling", shufflingId).
                build();
        JSONObject getShufflingResponse = apiCall.invoke();
        Logger.logMessage("getShufflingResponse: " + getShufflingResponse.toJSONString());
        Assert.assertEquals((long) Shuffling.Stage.DONE.getCode(), getShufflingResponse.get("stage"));

        apiCall = new APICall.Builder("shufflingDistribute").
                param("shuffling", shufflingId).
                param("secretPhrase", secretPhrase1).
                feeNQT(Constants.ONE_NXT).
                build();
        response = apiCall.invoke();
        Logger.logDebugMessage("shufflingDistributeResponse:" + response);
        Assert.assertTrue(((String)response.get("error")).contains("Shuffling not ready for distribution"));
    }

    private void tryCancel(String shufflingId) {
        APICall apiCall = new APICall.Builder("shufflingCancel").
                param("shuffling", shufflingId).
                param("secretPhrase", secretPhrase1).
                feeNQT(Constants.ONE_NXT).
                build();
        JSONObject response = apiCall.invoke();
        Logger.logDebugMessage("shufflingCancelResponse:" + response);
        Assert.assertTrue(((String) response.get("error")).contains("cannot be cancelled"));
    }

}

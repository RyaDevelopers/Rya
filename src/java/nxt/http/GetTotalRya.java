/*
 * Copyright © 2013-2016 The Nxt Core Developers.
 * Copyright © 2016-2017 Jelurida IP B.V.
 *
 * See the LICENSE.txt file at the top-level directory of this distribution
 * for licensing information.
 *
 * Unless otherwise agreed in a custom licensing agreement with Jelurida B.V.,
 * no part of the Nxt software, including this file, may be copied, modified,
 * propagated, or distributed except according to the terms contained in the
 * LICENSE.txt file.
 *
 * Removal or modification of this copyright notice is prohibited.
 *
 */

package nxt.http;

import nxt.Account;
import nxt.AccountLedger;
import nxt.Block;
import nxt.BlockchainProcessor;
import nxt.Constants;
import nxt.Nxt;
import nxt.peer.Peer;
import nxt.peer.Peers;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.servlet.http.HttpServletRequest;

import static nxt.Constants.ONE_NXT;

public final class GetTotalRya extends APIServlet.APIRequestHandler {

    static final GetTotalRya instance = new GetTotalRya();

    private GetTotalRya() {
        super(new APITag[] {APITag.BLOCKS, APITag.INFO});
    }

    @Override
    protected JSONObject processRequest(HttpServletRequest req) {
        JSONObject response = new JSONObject();
        response.put("totalSupply", Account.getTotalBalanceNQT());
        return response;
    }

    @Override
    protected boolean isPlainText() {
        return true;
    }

    @Override
    protected String processRequestString(HttpServletRequest request) {
        return new Long(Account.getTotalBalanceNQT()/ONE_NXT).toString();
    }

    @Override
    protected boolean allowRequiredBlockParameters() {
        return false;
    }

}

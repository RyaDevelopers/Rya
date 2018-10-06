/*
 * Copyright © 2013-2016 The Nxt Core Developers.
 * Copyright © 2016-2017 Jelurida IP B.V.
 * Copyright © 2017-2018 Rya Developers.
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
 */

package nxt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.simple.JSONObject;

import nxt.Account.AccountTrust;
import nxt.db.DbClause;
import nxt.db.DbIterator;
import nxt.db.DbKey;
import nxt.db.DbUtils;
import nxt.db.EntityDbTable;
import nxt.util.Convert;
import nxt.util.Logger;

public class TrustTransfer {
	private final long amount;
	private long recipientId;
    private int height = Integer.MAX_VALUE;

    public TrustTransfer(long amount, long recipientId, int height) {
    	this.amount = amount;
    	this.recipientId = recipientId;
    	this.height = height;
    }
    public long getRecipientId() {
        return recipientId;
    }
    public void setRecipientId(long recipientId) {
        this.recipientId = recipientId;
    }
    public long getHeight() {
        return height;
    }
    public void setHeight(int height) {
        this.height = height;
    }

    public long getAmount() {
        return amount;
    }
    
}

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
import nxt.Attachment;
import nxt.NxtException;
import nxt.util.Logger;

import org.json.simple.JSONStreamAware;

import javax.servlet.http.HttpServletRequest;

public final class SendLoan extends CreateTransaction {

    static final SendLoan instance = new SendLoan();

    private SendLoan() {
        super(new APITag[] {APITag.ACCOUNTS, APITag.CREATE_TRANSACTION}, "recipient", "amountNQT");
    }

    @Override
    protected JSONStreamAware processRequest(HttpServletRequest req) throws NxtException {
    	Logger.logDebugMessage("SendLoan:processRequest start");
        long recipient = ParameterParser.getAccountId(req, "recipient", true);
        long loanAmount = ParameterParser.getAmountNQT(req);
        int durationInBlocks = ParameterParser.getDurationInBlocks(req); // Get the loan duration in blocks unit
        long interestFee = ParameterParser.getInterestFeeNQT(req); // Get the loan interest in trust coins
        long totalAmout = loanAmount;
        Account account = ParameterParser.getSenderAccount(req);
        Attachment attachment = new Attachment.Loan(durationInBlocks,loanAmount,interestFee);
        Logger.logDebugMessage("SendLoan:processRequest succeed, creating transaction");
        JSONStreamAware reply = createTransaction(req, account, recipient, totalAmout, attachment); 
        return reply;
    }

}
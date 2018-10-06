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
 *
 */

package nxt.http;

import nxt.Account;
import nxt.Attachment;
import nxt.NxtException;
import org.json.simple.JSONStreamAware;
import nxt.util.Logger;

import javax.servlet.http.HttpServletRequest;

public final class SendPayBackLoan extends CreateTransaction {

    static final SendPayBackLoan instance = new SendPayBackLoan();

    private SendPayBackLoan() {
        super(new APITag[] {APITag.ACCOUNTS, APITag.CREATE_TRANSACTION}, "recipient", "amountNQT");
        Logger.logDebugMessage("SendPayBackLoan()");
    }

    @Override
    protected JSONStreamAware processRequest(HttpServletRequest req) throws NxtException {
        Logger.logDebugMessage("SendPayBackLoan:processRequest start");
        long loanId = ParameterParser.getLoanId(req);
        long payBackLoanAmount = ParameterParser.getAmountNQT(req);
        long payBackLoanFee = ParameterParser.getFeeNQT(req);
        Account account = ParameterParser.getSenderAccount(req);
        long recipient = ParameterParser.getAccountId(req, "recipient", true);
        Attachment attachment = new Attachment.PayBackLoan(loanId, payBackLoanAmount, payBackLoanFee);
        Logger.logDebugMessage("SendPayBackLoan: processRequest loanId = " + loanId);
        Logger.logDebugMessage("SendPayBackLoan: processRequest payBackLoanAmount = " + payBackLoanAmount);
        Logger.logDebugMessage("SendPayBackLoan: processRequest payBackLoanFee = " + payBackLoanFee);

        return createTransaction(req, account, recipient, payBackLoanAmount, attachment);
    }

}



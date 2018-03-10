var NRS = (function(NRS, $, undefined) {
	
    function setLoanPeriodHelp(period) {
        var days = Math.round(period / 1440);
        $("#loan_duration_help").html($.t("loan_duration_help_var", {
            "blocks": String(period).escapeHTML(),
            "days": String(Math.round(days)).escapeHTML()
        }));
    }

    $("#loan_duration").on("change", function() {
		if (this.value > NRS.constants.MAX_UNSIGNED_SHORT_JAVA) {
			$("#lease_balance_help").html($.t("loan_duration_minimal_value", {
                "minLoanDuration": 1 // TODO: change by const
            }));
		} else {
            setLoanPeriodHelp(this.value);
        }
	});

	return NRS;
}(NRS || {}, jQuery));

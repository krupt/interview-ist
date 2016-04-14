'use strict';

$.ajaxSetup({
    timeout: 30000
});

$(document).ready(function() {
    $('div.input-img.password-visible').each(function() {
		var $this = $(this),
			$pwd = $this.children("input[type='password']"),
			$icon = $this.children('i.fa-lock.text-warning');
		$icon.click(function() {
			if ($pwd.attr('type') == 'password')
				$pwd.attr('type', 'text');
			else
				$pwd.attr('type', 'password');
			$(this).toggleClass('fa-lock')
				.toggleClass('fa-unlock')
				.toggleClass('text-warning')
				.toggleClass('text-danger');
			$pwd.focus();
		});
	});
});

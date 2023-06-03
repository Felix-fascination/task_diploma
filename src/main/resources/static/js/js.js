$('.auth__input').on('input', function() {
	if(this.value) {
		$('.auth__entry').addClass('auth__entry_show');
	} else {
		$('.auth__entry').removeClass('auth__entry_show');
	}
});

$('.auth__input').on('keydown', function(e) {
	if((e.key === 'Enter') && $('.auth__input').val()) {
		alert('Вошли в систему');
	}
});

$('.auth__entry').on('click', function() {
	if($('.auth__input').val()) {
		var name = $('.auth__input').val();

		// Send AJAX request
		$.ajax({
			url: '/authenticate?name=' + name,
			type: 'POST',
			success: function() {
				window.location.href = '/main';
			},
			error: function() {
				// Handle error if needed
				alert('An error occurred during authentication.');
			}
		});
	}
});


$('.filter__input').on('focus', function() {
	$('.filter__selection').removeClass('filter__selection_active');
	$(this).parent().children('.filter__selection').addClass('filter__selection_active');
});

$('.filter__input').on('blur', function() {
	$('.filter__selection').removeClass('filter__selection_active');
});

$('.filter__input').on('keypress', function(e) {
	e.preventDefault();
});

$('.filter__option').on('click', function() {
	let isShowSearch = true;

	$('.filter__selection').removeClass('filter__selection_active');
	const selectItem = $(this).text();
	$(this).parents('.filter__item').children('.filter__input').val(selectItem);

	$('.filter__input').each(function() {
		if(!$(this).val()) {
			$('.filter__search').removeClass('filter__search_available').attr('tabindex', '-1');
			isShowSearch = false;
		}
	});

	if(isShowSearch) {
		$('.filter__search').addClass('filter__search_available').attr('tabindex', '0');
	}
});


$('.filter__search').on('click', function() {
	$('.filter').addClass('filter_active');
	$('.shedule').addClass('shedule_active');
	$('body').addClass('page_search');
	$(this).blur();
});

$('.event').on('mouseenter', function() {
	$('#' + $(this).data('column')).addClass('shedule__header_active');
	$('#' + $(this).data('row')).addClass('shedule__header_active');
});
$('.event').on('mouseleave', function() {
	$('#' + $(this).data('column')).removeClass('shedule__header_active');
	$('#' + $(this).data('row')).removeClass('shedule__header_active');
});
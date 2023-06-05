$('.auth__input').on('input', function() {
	if(this.value) {
		$('.auth__entry').addClass('auth__entry_show');
	} else {
		$('.auth__entry').removeClass('auth__entry_show');
	}
});

$('.auth__input').on('keydown', function(e) {
	if((e.key === 'Enter') && $('.auth__input').val()) {
		var name = $('.auth__input').val();
		$.ajax({
			url: '/authenticate?name=' + name,
			type: 'POST',
			success: function() {
				window.location.href = '/main';
			},
			error: function() {
				alert('An error occurred during authentication.');
			}
		});
	}
});

$('.auth__entry').on('click', function() {
	if($('.auth__input').val()) {
		var name = $('.auth__input').val();
		$.ajax({
			url: '/authenticate?name=' + name,
			type: 'POST',
			success: function() {
				window.location.href = '/main';
			},
			error: function() {
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

$(document).on('click','.filter__option', function() {
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

	const requestBody = {
		course: $('.filter__input[data-filter=course]').val(),
		faculty: $('.filter__input[data-filter=faculty]').val(),
		group: $('.filter__input[data-filter=group]').val(),
		isODD: $('#ODD').is(':checked'),
	}
	$.ajax({
		url: '/main/schedule/get',
		type: 'POST',
		data: JSON.stringify(requestBody),
		contentType: "application/json; charset=utf-8",
		success: function(response) {
			$('.shedule__body').html(response);
		},
	});

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


$(document).on('change','.event__message', function() {
	console.log($(this).val())
	var comment = $(this).val();
	var id = $(this).attr('data-textarea-id');
	console.log($(this).dataset)
	$.ajax({
		url: '/main/comment/post?comment='+ comment + '&paraId='+ id,
		type: 'POST',
		success: function() {
		},
		error: function() {
			alert('An error occurred during authentication.');
		}
	});
});

$(document).on('click','.filter__option', function() {
	const requestBody = {
		course: $('.filter__input[data-filter=course]').val(),
		faculty: $('.filter__input[data-filter=faculty]').val()
	};
	if(requestBody.course && requestBody.faculty) {
		$.ajax({
			url: '/main/groups/get',
			type: 'POST',
			data: JSON.stringify(requestBody),
			contentType: "application/json; charset=utf-8",
			success: function(response) {
				$('.filter__selection[data-filter-selection=group]').html(response);
			},

		});
	}
});
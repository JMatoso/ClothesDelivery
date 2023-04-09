$('.btn-filter').on('click', function() {
    const filter = $(this).data('filter');
    const $items = $('.product-item');

    $items
        .removeClass('show')
        .filter('.' + filter)
        .addClass('show');

    $items.filter('.show').slideUp(0).slideDown(500);
});
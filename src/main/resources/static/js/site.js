var token = $('meta[name=_csrf]').attr('content');
$.ajaxSetup({
    headers: {'X-CSRF-TOKEN': token}
});

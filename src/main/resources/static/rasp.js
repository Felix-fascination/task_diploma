$(document).ready(function () {
    $("form").submit(function (event) {
        event.preventDefault(); // Prevent default form submission
        var form = $(this);

        // Convert form data to JSON
        var formData = form.serializeArray();
        var data = {};
        $(formData).each(function(index, obj){
            data[obj.name] = obj.value;
        });
        var json = JSON.stringify(data);

        // Perform asynchronous POST request
        $.ajax({
            type: form.attr("method"),
            url: form.attr("action"),
            data: json,
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            error: function () {
                // Reloads page
                // Todo remake upon success
                window.location.reload(true);
            }
        });
    });
});

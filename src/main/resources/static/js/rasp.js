$(document).ready(function () {
    $("form").submit(function (event) {
        event.preventDefault(); // Prevent default form submission
        var form = $(this);

        // Convert form data to JSON
        var formData = form.serializeArray();
        var data = {};
        $(formData).each(function (index, obj) {
            data[obj.name] = obj.value;
        });

        // Retrieve "odd" value from URL query parameters
        var urlParams = new URLSearchParams(window.location.search);
        var oddParam = urlParams.get("odd");

        // Get the form action URL
        var actionUrl = form.attr("action");

        // Construct the new action URL with the "odd" parameter
        if (oddParam) {
            actionUrl += "?odd=" + encodeURIComponent(oddParam);
        }

        var json = JSON.stringify(data);

        // Perform asynchronous POST request with the updated action URL
        $.ajax({
            type: form.attr("method"),
            url: actionUrl,
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




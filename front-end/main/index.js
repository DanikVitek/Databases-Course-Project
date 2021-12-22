window.onload = function () {
    document
        .getElementById('commit')
        .addEventListener('click', showResult)
}

function showResult() {
    let id = document.getElementById('commit_text').innerText
    let url = 'http://localhost:8080/' + id
    customAjax(url, function (response) {
        let fistName = response.results
    })
}


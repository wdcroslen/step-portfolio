
// Load the Visualization API and the corechart package.
google.charts.load('current', {'packages':['corechart']});
// Set a callback to run when the Google Visualization API is loaded.
google.charts.setOnLoadCallback(drawChart);
google.charts.setOnLoadCallback(drawChartComments);




function drawChartComments() {
  fetch('/chart').then(response => response.json())
  .then((commentData) => {
    const data = new google.visualization.DataTable();
    data.addColumn('string', 'User Nickname');
    data.addColumn('number', 'Number of words typed');
    Object.keys(commentData).forEach((user) => {
      data.addRow([user, commentData[user]]);
    });

    const options = {
      'title': 'Comment Information by User',
      'width':800,
      'height':500
    };

    const chart = new google.visualization.PieChart(
        document.getElementById('comment-data'));
    chart.draw(data, options);
  });
}


//Adds data and draws chart
function drawChart() {
        // Create the data table.
        var data = new google.visualization.DataTable();
        data.addColumn('string', 'Movie');
        data.addColumn('number', 'Times Watched This Week');
        data.addRows([
          ['Shrek', 9],
          ['Onions', 1],
          ['Mulan',2],
          ['Shrek 2', 2],
          ['Spider Man Homecoming', 1],
          ['Avengers Infinity War', 2]
        ]);
        // Set chart options
        var options = {'title':'Movies I watched this week',
                       'width':800,
                       'height':500};

        // Instantiate and draw our chart, passing in some options.
        var chart = new google.visualization.PieChart(document.getElementById('chart_div'));
        chart.draw(data, options);
}




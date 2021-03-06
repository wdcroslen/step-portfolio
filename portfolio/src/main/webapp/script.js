// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.



// Load the Visualization API and the corechart package.
google.charts.load('current', {'packages':['corechart']});
// Set a callback to run when the Google Visualization API is loaded.
google.charts.setOnLoadCallback(drawChart);

//Adds data and draws chart
function drawChart() {

        // Create the data table.
        var data = new google.visualization.DataTable();
        data.addColumn('string', 'Movie');
        data.addColumn('number', 'Times Watched This Week');
        data.addRows([
          ['Shrek', 3],
          ['Onions', 1],
          ['Shrek 2', 1],
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



//hides and unhides comment button if user is logged in
function unHideComment() {
  const x = document.getElementById("hidden");
  if (x.style.display === "none") {
    x.style.display = "inline-block";
  } else {
    x.style.display = "none";
  }
}


function login(){
    fetch('/login').then(response => response.json()).then((login) => {
        const log = document.getElementById('login');
        log.innerText = login
  });
}


function deleteComments(){
    fetch('/delete-data').then(response => response.json()).then((tasks) => {
    const taskListElement = document.getElementById('task-list');
    tasks.forEach((task) => {
      taskListElement.remove();
    })
  });

}

function loadTasks() {
  fetch('/text?task-list=3').then(response => response.json()).then((tasks) => {
    const taskListElement = document.getElementById('task-list');
    tasks.forEach((task) => {
      taskListElement.appendChild(createListElement(task));
    })
  });
}

function addRandomGreeting() {
  const greetings =
      ['Hello world!', '¡Hola Mundo!', '你好，世界！', 'Bonjour le monde!'];

  // Pick a random greeting.
  const greeting = greetings[Math.floor(Math.random() * greetings.length)];

  // Add it to the page.
  const greetingContainer = document.getElementById('greeting-container');
  greetingContainer.innerText = greeting;
}


function addRandomMyFact() {
  const facts =
      ['I love cinammon Toast Crunch!', 'I like to play piano in my free time!', 'I want to travel  the world!'];

  // Pick a random greeting.
  const fax = facts[Math.floor(Math.random() * facts.length)];

  // Add it to the page.
  const factContainer = document.getElementById('fact-container');
  factContainer.innerText = fax;
}


function fetchMessage() {
    console.log("fetch");
    fetch('/data').then(response => response.text()).then((word) => {
    document.getElementById('word-container').innerText = word;
  });
}

function fetchJSON() {
    fetch('/data').then(response => response.json()).then((words) => {
 const listElement = document.getElementById('JSON-container');
    listElement.innerHTML = '';
    listElement.appendChild(
        createListElement('First Element: ' + words.first));
    listElement.appendChild(
        createListElement('Second Element: ' + words.second));
    listElement.appendChild(
        createListElement('Third Element: ' + words.third));
  });


}
function createListElement(text) {
  const liElement = document.createElement('li');
  liElement.innerText = text;
  return liElement;
}

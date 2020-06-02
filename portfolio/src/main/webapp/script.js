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

/**
 * Adds a random greeting to the page.
 */
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
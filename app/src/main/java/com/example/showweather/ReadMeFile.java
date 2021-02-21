package com.example.showweather;

public class ReadMeFile {
    /*
    API is termed as "application programming interface" .the developer creates an API on the server
    and allows the client to talk to it.

    REST determines how my API should look like .It stands for "Representational State Transfer” .there
    are some rules which developers follow while forming their API
    one such rule could be..when you link to a specific URL you should get a piece of data

    Each URL is a request and the data which comes back is called as response
    Volley Library is a HTTP library which makes networking faster and easier .
    earlier we have "Async Task" which works like--
    {
        when user clicks the button application sends request --> doInbackground() method of async class
         or worker thread ---> which then sends HTTp request to the server ---> response id fetched and
          send to worker thread---> this thread then sends the data to the main thread --> which displays
           the data to the UI.

           bt...
           when we rotate our screen the whole process goes again
           when we compare it with Volley Lib there we have an advantage of caching..
            we have three thread here main thread,cashing thread, and worker thread
           process goes like
           button clicks---> request is made to cashe dispatcher ---> request to cache thread --->checks if data is present in cache memory
           if response is received --> cache thread-->cache dispatcher-->main thread
           else
           cache miss is sent to cache dispatcher-->cache dispatcher sends the request to network thread
           ---> gets the response from the server --> sends it to cache dispatcher--> cache thread--> cache memory and at the same time to the mian thread

           so when the same request ( changing the orientation of mobile )is made is will check that in the cache memory hence improving the user experience


    }


















    */


}

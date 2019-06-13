# rxandroid_retrofit_poc

Proof-of-concept in order to get more fluent with RxAndroid, Retrofit and GSON. Aspects of this will be used to create a quiz application which retrieves its questions from an API.

Once the observable API call in TestViewModel (service.getQuizResults) returns a JSON response (which is then mapped to a POJO using gson), the observer assigns that result to the MutableLiveData member variable mQuestions from a background thread. Since mQuestions is being observed in TestActivity for changes, its UI is then updated to display the first question contained in the result of the API call.


  /*!
   * Create an array of word objects, each representing a word in the cloud
   */
  var word_array = [
      {text: "Lorem", weight: 15},
  {text: "Ipsum", weight: 9, link: "http://jquery.com/"},
  {text: "Dolor", weight: 6, html: {title: "I can haz any html attribute"}},
  {text: "Sit", weight: 7},
  {text: "Amet", weight: 5}
  // ...as many words as you want
  ];

  $(function() {
	  var listResult = $(".result");
	  $.each(listResult,function(index,value){
		 var json = extractEntities(value);
		 var cloud = $(value).find(".nuage-word");
		 $(cloud).jQCloud(json);
	  });
	  //console.log(listResult);
	  console.log("okt");
    // When DOM is ready, select the container element and call the jQCloud method, passing the array of words as the first argument.
	  
  });

 var extractEntities = function(resultDiv){
	 var jsonWord = [];
	 var listEntities = $(resultDiv).find(".list-entities li").slice(0,10);
	 $.each(listEntities,function(index,value){
		var entity = $(value).find("span.entity-value").html();
		var nbr = $(value).find(".entity-nbr").html();
		var item = {};
		item["text"] = entity;
		item["weight"] = nbr;
		jsonWord.push(item);
		//console.log(JSON.stringify(jsonWord));
	 });
	// console.log(listEntities);
	 return jsonWord;
	 
 }
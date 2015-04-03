<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<head>
<meta charset="utf-8">
<base href="<%=request.getContextPath()%>/">
<title>show</title>
<script src="<%=request.getContextPath()%>/commons/js/jquery-1.11.1.js" type="text/javascript"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/commons/js/go-debug.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/commons/js/sockjs/sockjs.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/commons/js/stomp/stomp.js"></script>
<script type="text/javascript">

function init(){
	connect();
}
function connect1(){
	var sock = new SockJS("<%=request.getContextPath()%>/wserver");
	sock.onopen = function() {
	    console.log('open');
	};
	sock.onmessage = function(e) {
	    console.log('message', e.data);
	};
	sock.onclose = function() {
	    console.log('close');
	};
	sock.send('test');
	sock.close();
}



function connect(){
	//验证浏览器是否支持WebSocket协议  
	if(!window.WebSocket){  
	alert("WebSockeet not supported by this browser!");  
	}

	var socket=new SockJS("<%=request.getContextPath()%>/web/wserver");   
	var stompClient = Stomp.over(socket);
	window.stompClient = stompClient;
	
	stompClient.connect({}, function(frame) {

    console.log('Connected ' + frame);

    stompClient.subscribe("/app/pmsg", function(message) {
    	showMsg(JSON.parse(message.body));
    });
    
    stompClient.subscribe("/topic/door", function(message) {
    	showDoorMsg(JSON.parse(message.body));
      });
    stompClient.subscribe("/queue/pmsg", function(message) {
    	showMsg(JSON.parse(message.body));
      });
    
  }, function(error) {
    console.log("STOMP protocol error:" + error);
  });
	
	//stompClient.send("/sendmsg", {}, "test mesage");

}

function showDoorMsg(msg){
	var m = msg.doorId+'门被'+msg.operation;
	showMsg(m);
	//调整门
	var model = window.myDiagram.model;
    model.startTransaction("update locations");
    
    var data = model.findNodeDataForKey(msg.doorId);
    
    var c = msg.operation=="open"?"red":"green";
    model.setDataProperty(data, "color",c);
    
    model.commitTransaction("update locations");
}

function showMsg(msg){
	console.log(msg);
	$('#mesages').html(msg);
}

function sendMsg(msg){
	var msg = $('#msg').val();
	console.log('send message:'+msg);
	window.stompClient.send("/app/sendmsg", {}, JSON.stringify(msg));
}

function goInit() {
	var $ = go.GraphObject.make;  // for conciseness in defining templates

    myDiagram =
      $(go.Diagram, "myDiagram",
        {
          initialContentAlignment: go.Spot.TopLeft,
          isReadOnly: true,  // allow selection but not moving or copying or deleting
          "toolManager.hoverDelay": 100  // how quickly tooltips are shown
        });
	
	window.myDiagram = myDiagram;
	
 // the background image, a floor plan
    myDiagram.add(
      $(go.Part,  // this Part is not bound to any model data
        { layerName: "Background", position: new go.Point(0, 0),
          selectable: false, pickable: false },
        $(go.Picture, "commons/images/Sample_Floorplan.jpg")));
 
 // the template for each kitten, for now just a colored circle
    myDiagram.nodeTemplate =
      $(go.Node,
        new go.Binding("location", "loc"),  // specified by data
        { locationSpot: go.Spot.Center },   // at center of node
        $(go.Shape, "Circle",
          { width: 12, height: 12, stroke: null },
          new go.Binding("fill", "color")),  // also specified by data
        { // this tooltip shows the name and picture of the kitten
          toolTip:
            $(go.Adornment, "Auto",
              $(go.Shape, { fill: "lightyellow" }),
              $(go.Panel, "Vertical",
                $(go.Picture,
                  new go.Binding("source", "src", function(s) { return "commons/images/" + s + ".png"; })),
                $(go.TextBlock, { margin: 3 },
                  new go.Binding("text", "key"))
              )
            )  // end Adornment
        }
      );
 
 // pretend there are four kittens
    myDiagram.model.nodeDataArray = [
      { key: "A1",src: "50x40", loc: new go.Point(207, 301), color: "green" },
      { key: "A2",src: "50x40", loc: new go.Point(321, 368), color: "green" },
      { key: "A3",src: "50x40", loc: new go.Point(205, 445), color: "green" },
      { key: "A4",src: "50x40", loc: new go.Point(576, 369), color: "green" }
    ];
 
 
 // simulate some real-time position monitoring, once every 2 seconds
    function randomMovement() {
      var model = myDiagram.model;
      model.startTransaction("update locations");
      var arr = model.nodeDataArray;
      var picture = myDiagram.parts.first();
      for (var i = 0; i < arr.length; i++) {
        var data = arr[i];
        var pt = data.loc;
        var x = pt.x + 20 * Math.random() - 10;
        var y = pt.y + 20 * Math.random() - 10;
        // make sure the kittens stay inside the house
        var b = picture.actualBounds;
        if (x < b.x || x > b.right) x = pt.x;
        if (y < b.y || y > b.bottom) y = pt.y;
        model.setDataProperty(data, "loc", new go.Point(x, y));
      }
      model.commitTransaction("update locations");
    }
    //randomMovement();
}

</script>
</head>
<body>
<div>
<input id="msg" name="msg" type="text" value="test message"><input type="button" value="send" onclick="javascript:sendMsg();">
<div id="mesages" style="display:inline;"></div>
</div>
<div id="sample">
  <h3>Monitor</h3>
  <div id="myDiagram" style="border: 1px solid black; border-image: none; width: 100%; height: 550px;"></div>
  <p>The tooltip for each door shows its name and photo.</p>
</div>

    <script type="text/javascript">
      (function() {
        init();
        goInit();
      })();
    </script>
</body>
</html>
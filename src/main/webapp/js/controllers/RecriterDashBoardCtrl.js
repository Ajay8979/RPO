app.controller("RecriterDashBoardCtrl",function($scope,RecuriterDashBoard, $location,$timeout) { 
	 $scope.showtable = function() {
	        $scope.mytable = !$scope.mytable;
	    };
	    $scope.showtable2 = function() {
	        $scope.mytable2 = !$scope.mytable2;
	    };
	    $scope.showtable3 = function() {
	        $scope.mytable3 = !$scope.mytable3;
	    };
	    $scope.productivitytable1 = function() {
	        $scope.protable1 = !$scope.protable1;
	    };
	    $scope.productivitytable2 = function() {
	        $scope.protable2 = !$scope.protable2;
	    };
	    $scope.revenuetable1 = function() {
	        $scope.revenueview1 = !$scope.revenueview1;
	    };
	    $scope.revenuetable2 = function() {
	        $scope.revenueview2 = !$scope.revenueview2;
	    };
	    $scope.revenuetable3 = function() {
	        $scope.revenueview3 = !$scope.revenueview3;
	    };
	    $scope.revenuetable4 = function() {
	        $scope.revenueview4 = !$scope.revenueview4;
	    };
	    $scope.revenuetable5 = function() {
	        $scope.revenueview5 = !$scope.revenueview5;
	    };
	    $scope.revenuetable6 = function() {
	        $scope.revenueview6 = !$scope.revenueview6;
	    };
	    
	    //Quality reports starts//
	    
	    //1.Submissions vs Rejections starts //
	    
	    $scope.chartdata=function(){
			var dataSource = [{
			    state: "Submissions",
			    year2018: "42",
			    year2019: "47",
			   
			}, {
			    state: "Rejections",
			    year2018: "20",
			    year2019: "12",
			    
			}
			]; 
			$scope.datavalue=dataSource;
			console.log($scope.datavalue);
			$scope.chartOptions1 = {
				        dataSource: dataSource,
				        palette:  ['#e8a742', '#285484', '#e49316', '#034a96', '#e8a742', '#285484', '#034a96', '#034a96','#e8a742', '#e49316', '#285484', '#034a96'],
				        commonSeriesSettings: {
				            argumentField: "state",
				            type: "bar",
				            
				        },
				        series: [
				            { valueField: "year2018", name: "2018" },
				            { valueField: "year2019", name: "2019" }
				        ],
				        title: "Submissions vs Rejections",
				        
				        "export": {
				            enabled: false
				        },
				        tooltip: {
				            enabled: true,
				            location: "bottom",
				            customizeTooltip: function (arg) {
				                return {
				                    text: arg.seriesName + " : " + arg.valueText
				                };
				            }
				        },
				        onPointClick: function (e) {
				            e.target.select();
				        }
				    };
		}
	    
	  //1.Submissions vs Rejections ends //
	    
	  //2. Submissions vs Closure starts//
		$scope.chartstack=function(){
			var dataSource2 = [{
			    state: "Submissions",
			    year2018: 42,
			    year2019: 47,
			   
			}, {
			    state: "Closure",
			    year2018: 10,
			    year2019: 39,
			    
			}
			]; 
			$scope.datavalue2=dataSource2;
			console.log($scope.datavalue2);
			$scope.chartOptions2 = {
				        dataSource: dataSource2,
				        palette:  ['#e8a742', '#285484', '#e49316', '#034a96', '#e8a742', '#285484', '#034a96', '#034a96','#e8a742', '#e49316', '#285484', '#034a96'],
				        commonSeriesSettings: {
				            argumentField: "state",
				            type: "bar",
				            
				        },
				        series: [
				            { valueField: "year2018", name: "2018" },
				            { valueField: "year2019", name: "2019" }
				        ],
				        title: "Submissions vs Closure",
				        
				        "export": {
				            enabled: false
				        },
				        tooltip: {
				            enabled: true,
				            location: "bottom",
				            customizeTooltip: function (arg) {
				                return {
				                    text: arg.seriesName + " : " + arg.valueText
				                };
				            }
				        },
				        onPointClick: function (e) {
				            e.target.select();
				        }
				    };
		}
		//2. Submissions vs Closure end //
	    
		//3.  Closing vs Joining start//
		$scope.CvsJ=function(){
			var dataSource3 = [{
			    state: "Closure",
			    year2018: 30,
			    year2019: 47,
			   
			}, {
			    state: "Joining",
			    year2018: 40,
			    year2019: 49,
			    
			}
			]; 
			$scope.datavalue3=dataSource3;
			console.log($scope.datavalue3);
			$scope.chartOptions3 = {
				        dataSource: dataSource3,
				        palette:  ['#e8a742', '#285484', '#e49316', '#034a96', '#e8a742', '#285484', '#034a96', '#034a96','#e8a742', '#e49316', '#285484', '#034a96'],
				        commonSeriesSettings: {
				            argumentField: "state",
				            type: "bar",
				            
				        },
				        series: [
				            { valueField: "year2018", name: "2018" },
				            { valueField: "year2019", name: "2019" }
				        ],
				        title: "Closure vs Joining",
				        
				        "export": {
				            enabled: false
				        },
				        tooltip: {
				            enabled: true,
				            location: "bottom",
				            customizeTooltip: function (arg) {
				                return {
				                    text: arg.seriesName + " : " + arg.valueText
				                };
				            }
				        },
				        onPointClick: function (e) {
				            e.target.select();
				        }
				    };
		}
		
		//3. Closing vs Joining End //
		
	  //Quality reports ends//
		
		
		// productivity charts start //
		
		//1.  Requirement vs Submissions start//
		$scope.productivity1=function(){
			var dataSource4 = [{
			    state: "Requirement",
			    year2018: 30,
			    year2019: 47,
			   
			}, {
			    state: "Submissions",
			    year2018: 40,
			    year2019: 49,
			    
			}
			]; 
			$scope.provalue1=dataSource4;
			console.log($scope.provalue1);
			$scope.productivity1 = {
				        dataSource: dataSource4,
				        palette:  ['#e8a742', '#285484', '#e49316', '#034a96', '#e8a742', '#285484', '#034a96', '#034a96','#e8a742', '#e49316', '#285484', '#034a96'],
				        commonSeriesSettings: {
				            argumentField: "state",
				            type: "bar",
				            
				        },
				        series: [
				            { valueField: "year2018", name: "2018" },
				            { valueField: "year2019", name: "2019" }
				        ],
				        title: " Requirement vs Submissions",
				        
				        "export": {
				            enabled: false
				        },
				        tooltip: {
				            enabled: true,
				            location: "bottom",
				            customizeTooltip: function (arg) {
				                return {
				                    text: arg.seriesName + " : " + arg.valueText
				                };
				            }
				        },
				        onPointClick: function (e) {
				            e.target.select();
				        }
				    };
		}
		
		//1. Requirement vs Submissions End //
		
		
		//2.  Requirement vs BDM start//
		$scope.productivity2=function(){
			var dataSource5 = [{
			    state: "Requirements",
			    year2018: 30,
			    year2019: 47,
			   
			}, {
			    state: "BDM",
			    year2018: 40,
			    year2019: 49,
			    
			}
			]; 
			$scope.provalue2=dataSource5;
			console.log($scope.provalue2);
			$scope.productivity2 = {
				        dataSource: dataSource5,
				        palette:  ['#e8a742', '#285484', '#e49316', '#034a96', '#e8a742', '#285484', '#034a96', '#034a96','#e8a742', '#e49316', '#285484', '#034a96'],
				        commonSeriesSettings: {
				            argumentField: "state",
				            type: "bar",
				            
				        },
				        series: [
				            { valueField: "year2018", name: "2018" },
				            { valueField: "year2019", name: "2019" }
				        ],
				        title: " Requirements vs BDM",
				        
				        "export": {
				            enabled: false
				        },
				        tooltip: {
				            enabled: true,
				            location: "bottom",
				            customizeTooltip: function (arg) {
				                return {
				                    text: arg.seriesName + " : " + arg.valueText
				                };
				            }
				        },
				        onPointClick: function (e) {
				            e.target.select();
				        }
				    };
		}
		
		//2. Requirements vs BDM End //
		
		
		// productivity charts end //
		
		
		
		
		
		// Revenue Charts Starts //
		
		//1.Revenue by Category starts //
		
		$scope.Revenuedata1=function(){
			var RevenueSource1 = [{
			    region: "UI",
			    val: 411
			}, {
			    region: "JAVA",
			    val: 1012
			}, {
			    region: "DotNet",
			    val: 344
			}, {
			    region: "Testing",
			    val: 590
			}, {
			    region: "SQL",
			    val: 727
			}, {
			    region: "NodeJs",
			    val: 351
			}]; 
			$scope.revenuevalue1=RevenueSource1;
			console.log($scope.revenuevalue1);
			$scope.revenuechart1 = {
			        type: "doughnut",
			        palette:  ['#e8a742', '#285484', '#e49316', '#034a96', '#e8a742', '#285484', '#034a96', '#034a96','#e8a742', '#e49316', '#285484', '#034a96'],
			        dataSource: RevenueSource1,
			        title: "Revenue by Category",
			        tooltip: {
			            enabled: true,
			            location: "bottom",
			            customizeTooltip: function (arg) {
			                return {
			                    text: arg.argumentText + " : " + arg.valueText
			                };
			            }
			        },
			        
			        "export": {
			            enabled: false
			        },
			        series: [{            
			            argumentField: "region",
			            label: {
			                visible: true,
			                connector: {
			                    visible: true
			                }
			            }
			        }]
			    };
		}
		
		
		//1.Revenue by Category end //
		
		
		//2.Revenue by recuiter start //
		
		$scope.Revenuedata2=function(){
			var RevenueSource2 = [{
			    region: "abc",
			    val: 4000
			}, {
			    region: "xyz",
			    val: 1012
			}, {
			    region: "mln",
			    val: 3440
			}, {
			    region: "pqo",
			    val: 5900
			}, {
			    region: "ghi",
			    val: 7270
			}, {
			    region: "def",
			    val: 3510
			}]; 
			$scope.revenuevalue2=RevenueSource2;
			console.log($scope.revenuevalue2);
			$scope.revenuechart2 = {
			        type: "doughnut",
			        palette:  ['#e8a742', '#285484', '#e49316', '#034a96', '#e8a742', '#285484', '#034a96', '#034a96','#e8a742', '#e49316', '#285484', '#034a96'],
			        dataSource: RevenueSource2,
			        title: "Revenue by Recuiter",
			        tooltip: {
			            enabled: true,
			            location: "bottom",
			            customizeTooltip: function (arg) {
			                return {
			                    text: arg.argumentText + " : " + arg.valueText
			                };
			            }
			        },
			        
			        "export": {
			            enabled: false
			        },
			        series: [{            
			            argumentField: "region",
			            label: {
			                visible: true,
			                connector: {
			                    visible: true
			                }
			            }
			        }]
			    };
		}
		
		
		//2.Revenue by recuiter end //
		
		//3.Revenve by Customer start //
		
		$scope.Revenuedata3=function(){
			var RevenueSource3 = [{
			    region: "TCS",
			    val: 4000
			}, {
			    region: "Capgemini",
			    val: 1012
			}, {
			    region: "CTS",
			    val: 3440
			}, {
			    region: "Glogal Logic",
			    val: 5900
			}, {
			    region: "HCL",
			    val: 7270
			}, {
			    region: "Gspann",
			    val: 3510
			}]; 
			$scope.revenuevalue3=RevenueSource3;
			console.log($scope.revenuevalue3);
			$scope.revenuechart3 = {
			        type: "doughnut",
			        palette:  ['#e8a742', '#285484', '#e49316', '#034a96', '#e8a742', '#285484', '#034a96', '#034a96','#e8a742', '#e49316', '#285484', '#034a96'],
			        dataSource: RevenueSource3,
			        title: "Revenve by Customer",
			        tooltip: {
			            enabled: true,
			            location: "bottom",
			            customizeTooltip: function (arg) {
			                return {
			                    text: arg.argumentText + " : " + arg.valueText
			                };
			            }
			        },
			        
			        "export": {
			            enabled: false
			        },
			        series: [{            
			            argumentField: "region",
			            label: {
			                visible: true,
			                connector: {
			                    visible: true
			                }
			            }
			        }]
			    };
		}
		
		
		//3.Revenve by Customer end //
		
		//4.Revenue By BDM start //
		
		$scope.Revenuedata4=function(){
			var RevenueSource4 = [{
			    region: "ABC",
			    val: 400
			}, {
			    region: "XYZ",
			    val: 102
			}, {
			    region: "DEF",
			    val: 344
			}, {
			    region: "GHI",
			    val: 590
			}, {
			    region: "MLN",
			    val: 720
			}, {
			    region: "PQO",
			    val: 351
			}]; 
			$scope.revenuevalue4=RevenueSource4;
			console.log($scope.revenuevalue4);
			$scope.revenuechart4 = {
			        
			        palette:  ['#e8a742', '#285484', '#e49316', '#034a96', '#e8a742', '#285484', '#034a96', '#034a96','#e8a742', '#e49316', '#285484', '#034a96'],
			        dataSource: RevenueSource4,
			        title: "Revenue By BDM",
			        tooltip: {
			            enabled: true,
			            location: "bottom",
			            customizeTooltip: function (arg) {
			                return {
			                    text: arg.argumentText + " : " + arg.valueText
			                };
			            }
			        },
			        
			        "export": {
			            enabled: false
			        },
			        series: [{            
			            argumentField: "region",
			            label: {
			                visible: true,
			                connector: {
			                    visible: true
			                }
			            }
			        }]
			    };
		}
		
		
		//4.Revenue By BDM end //
		
		//5.Revenue By lead(sum of recuiters  start//
		
		$scope.Revenuedata5=function(){
			var RevenueSource5 = [{
			    region: "ABC",
			    val: 4005
			}, {
			    region: "XYZ",
			    val: 1025
			}, {
			    region: "DEF",
			    val: 3445
			}, {
			    region: "GHI",
			    val: 5905
			}, {
			    region: "MLN",
			    val: 7205
			}, {
			    region: "PQO",
			    val: 3515
			}]; 
			$scope.revenuevalue5=RevenueSource5;
			console.log($scope.revenuevalue5);
			$scope.revenuechart5 = {
			        
			        palette:  ['#e8a742', '#285484', '#e49316', '#034a96', '#e8a742', '#285484', '#034a96', '#034a96','#e8a742', '#e49316', '#285484', '#034a96'],
			        dataSource: RevenueSource5,
			        title: "Revenue By Lead",
			        tooltip: {
			            enabled: true,
			            location: "bottom",
			            customizeTooltip: function (arg) {
			                return {
			                    text: arg.argumentText + " : " + arg.valueText
			                };
			            }
			        },
			        
			        "export": {
			            enabled: false
			        },
			        series: [{            
			            argumentField: "region",
			            label: {
			                visible: true,
			                connector: {
			                    visible: true
			                }
			            }
			        }]
			    };
		}
		
		
		//5.Revenue By lead(sum of recuiters  end//
		
		//6.Revenue By Period start //
		
		$scope.Revenuedata6=function(){
			var RevenueSource6 = [{
			    region: "2014",
			    val: 4005
			}, {
			    region: "2015",
			    val: 1025
			}, {
			    region: "2016",
			    val: 3445
			}, {
			    region: "2017",
			    val: 5905
			}, {
			    region: "2018",
			    val: 7205
			}, {
			    region: "2019",
			    val: 3515
			}]; 
			$scope.revenuevalue6=RevenueSource6;
			console.log($scope.revenuevalue6);
			$scope.revenuechart6 = {
			        
			        palette:  ['#e8a742', '#285484', '#e49316', '#034a96', '#e8a742', '#285484', '#034a96', '#034a96','#e8a742', '#e49316', '#285484', '#034a96'],
			        dataSource: RevenueSource6,
			        title: "Revenue By Period",
			        tooltip: {
			            enabled: true,
			            location: "bottom",
			            customizeTooltip: function (arg) {
			                return {
			                    text: arg.argumentText + " : " + arg.valueText
			                };
			            }
			        },
			        
			        "export": {
			            enabled: false
			        },
			        series: [{            
			            argumentField: "region",
			            label: {
			                visible: true,
			                connector: {
			                    visible: true
			                }
			            }
			        }]
			    };
		}
		
		//6.Revenue By period end //
		
		
		
		// Revenue Charts End //
	    
	
})
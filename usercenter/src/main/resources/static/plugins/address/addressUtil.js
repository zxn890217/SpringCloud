addressUtil = {};
/**获取省级地址列表*/
addressUtil.getProvinces = function(){
	return address_provinces;
};
/**根据省级地址ID获取市级地址列表*/
addressUtil.getCities = function(pid){
	var cities = [];
	for(var i=0;i<address_cities.length;i++){
		if(address_cities[i].p==pid)
			cities.push(address_cities[i]);
	}
	return cities;
};
/**根据市级地址ID获取区级地址列表*/
addressUtil.getRegions = function(cid){
	var districts = [];
	for(var i=0;i<address_regions.length;i++){
		if(address_regions[i].p==cid)
			districts.push(address_regions[i]);
	}
	return districts;
};
/**根据地区ID获取以市级ID*/
addressUtil.getCityId = function(districtId){
	for(var i=0;i<address_regions.length;i++){
		if(address_regions[i].i==districtId)
			return address_regions[i].p;
	}
};
/**根据市级ID获取以省级ID*/
addressUtil.getProvinceId = function(cityId){
	for(var i=0;i<address_cities.length;i++){
		if(address_cities[i].i==cityId)
			return address_cities[i].p;
	}
};
/**
 * 根据地区码获取地址
 */
addressUtil.getAddressByCode = function(code){
	code = code+"";
	var pcode = code.substring(0,2)+"0000";
	var ccode = code.substring(0,4)+"00";
	var address = {};
	for(var i=0;i<address_provinces.length;i++){
		if(address_provinces[i].c==pcode){
			address.province = address_provinces[i];
			break;
		}
	}
	if(code.indexOf("0000")<0){
		for(var i=0;i<address_cities.length;i++){
			if(address_cities[i].c==ccode){
				address.city = address_cities[i];
				break;
			}
		}
		if(code.indexOf("00")<0){
			for(var i=0;i<address_regions.length;i++){
				if(address_regions[i].c==code){
					address.region = address_regions[i];
					break;
				}
			}
		}
	}
	return address;
};
/**
 * 根据省级地区码获取省级地址
 * */
addressUtil.getProvinceByCode = function(code){
	for(var i=0;i<address_provinces.length;i++){
		if(address_provinces[i].c==code){
			return address_provinces[i];
		}
	}
};
/**
 * 根据市级地区码获取市级地址
 * */
addressUtil.getCityByCode = function(code){
	for(var i=0;i<address_cities.length;i++){
		if(address_cities[i].c==code){
			return address_cities[i];
		}
	}
};
/**
 * 根据区级地区码获取区级地址
 * */
addressUtil.getRegionByCode = function(code){
	for(var i=0;i<address_regions.length;i++){
		if(address_regions[i].c==code){
			return address_regions[i];
		}
	}
};

addressUtil.loadData = function(control,data){
	control.html("");
	control.append('<option value="">--选择--</option>');
	$.each(data, function(index,item){
		control.append('<option rel="'+item.i+'" value="'+item.c+'">'+item.n+'</option>');
	});
};

addressUtil.treeDialog = function(options){
	BootstrapDialog.show({
		id: options.dialogId,
		title: options.dialogTitle,
		closable: true,
		message: function(dialog) {
            var $tree = $('<ul id="'+options.treeId+'" class="ztree" style="overflow:auto;"></ul>');
            var defualtSettings = {
            		view: {
			            dblClickExpand: false,
			            showLine: true,
			            selectedMulti: false
			        },
			        check: {
			    		enable: true,
			    		chkStyle: "checkbox",
			    		radioType: "all"
			    	},
			        data: {
			        	key: {
			        		name: "n"
			        	},
			            simpleData: {
			                enable: true,
			                idKey: "i"
			            }
			        }
            	};
            var settings = $.extend(defualtSettings, options.settings || {});
            $.fn.zTree.init($tree, settings, addressUtil.loadCitiesAddressTreeData());
            return $tree;
        },
        buttons: options.buttons,
        onshown: function(dialog){
        	if(options.onshownFn){
        		options.onshownFn(dialog);
        	}
        }
    });
};
addressUtil.loadCitiesAddressTreeData = function(){
	var provinces = [];
	for(var i=0;i<address_provinces.length;i++){
		var p = jQuery.extend({}, address_provinces[i]);
		p.children = [];
		for(var j=0;j<address_cities.length;j++){
			if(address_cities[j].p==p.i){
				var c = jQuery.extend({}, address_cities[j]);
				p.children.push(c);
			}
		}
		provinces.push(p);
	}
	return {"i":0,"n":"全国","children":provinces};
};

<template>
    <div ref="container"></div>
</template>

<script>
import axios from 'axios'

    export default {
        data(){
            return {
                isDrawing : false,
                tool: 'pencil',     
                item: null,
                shape : 'square',
                color : 'black',
                filling: 'rgba(0,0,0,0)',
                size : 2,
                lastStroke : 'black',
                shapes : [this.stage],
                index : 1,
                colorSlot :0,
                lastFill : 'black',
                isPosted: [true],
                url : 'http://localhost:8080/',
                lastShape:null
            }
        },
        methods:{
            async startDrawing(e){
                if (e.target!=this.stage) e.target.draggable(false)
                if(this.tr.nodes().length > 0) return false
                if((this.index != 1 )&& !this.isPosted[this.lastShape.id()]){
                    const responsej = await axios.post( this.url +'paint/create',this.createPost(this.shapes[this.lastShape.id()]))
                    this.isPosted[this.shapes.length - 1] = true
                }
                
                this.isDrawing = true;
                
                if(this.tool == 'shape'){     
                    
                    if (this.shape== 'circle'){
                        this.item = new Konva.Circle({
                        radius : 0,
                        });
                    }
                    else if (this.shape=='ellipse'){
                        this.item = new Konva.Ellipse({
                        radiusX :0,
                        radiusY : 0,
                        });
                    }
                    else if (this.shape=='rectangle'||this.shape=='square'){
                        this.item = new Konva.Rect({
                        width :0,
                        height : 0,
                        });
                    }
                    else if (this.shape=='triangle'){
                        this.item = new Konva.RegularPolygon({
                        sides: 3,
                        radius: 0,
                        });
                    }
                    else if (this.shape=='line'){
                        this.item = new Konva.Line({
                        points : [this.stage.getPointerPosition().x,this.stage.getPointerPosition().y],
                        lineCap: 'round',
                        lineJoin: 'round',
                        });
                    }
                    if (this.shape!='line'){
                        this.item.x(this.stage.getPointerPosition().x)
                        this.item.y(this.stage.getPointerPosition().y)
                    }

                    this.item.name(this.shape) 
                }

                else if(this.tool == 'pencil'){
                    this.item = new Konva.Line({
                        points : [this.stage.getPointerPosition().x,this.stage.getPointerPosition().y],
                        lineCap:  'round',
                        lineJoin: 'round',
                        tension :  0.5,
                        name: 'pencil'
                    })
                }

                this.item.stroke(this.color)
                this.item.strokeWidth(this.size)
                this.item.id(this.index++)
                this.shapes.push(this.item)
                this.isPosted[this.item.id()] = false
                this.layer.add(this.item).batchDraw();
                this.lastShape = this.item
            },

            async endDrawing(){
                this.isDrawing = false;
                
            },

            continueDrawing(){
                if (!this.isDrawing) return false;
                else{
                    
                    if(this.tool == 'shape'){
                        if (this.shape=='rectangle'||this.shape=='square'||this.shape=='ellipse'){
                            const newWidth = this.stage.getPointerPosition().x-this.item.x();
                            const newHeight = this.stage.getPointerPosition().y-this.item.y();
                            if (this.shape=='ellipse'){
                                this.item.radiusX(Math.abs(newWidth)).radiusY(Math.abs(newHeight))
                                this.item.radiusY(Math.abs(newHeight))
                            }
                            else if (this.shape=='rectangle'){
                                this.item.width(newWidth).height(newHeight)
                                this.item.height(newHeight)
                            }
                            else {
                                if (newHeight<0 ^ newWidth<0) 
                                this.item.width(newWidth).height(-newWidth)
                                else
                                this.item.width(newWidth).height(newWidth)
                            }
                        }
                        else if (this.shape=='circle'||this.shape=='triangle'){
                            const xSquare = Math.pow(this.stage.getPointerPosition().x-this.item.x(),2)
                            const ySquare = Math.pow(this.stage.getPointerPosition().y-this.item.y(),2)
                            const newRadius = Math.sqrt (xSquare+ySquare)
                            this.item.radius(newRadius);
                        }
                        else if (this.shape=='line'){
                            const newX = this.stage.getPointerPosition().x
                            const newY = this.stage.getPointerPosition().y
                            this.item.points()[2]=(newX)
                            this.item.points()[3]=(newY)
                        }
                        
                        
                    }
                    else if(this.tool == 'pencil'){
                        const pos = this.stage.getPointerPosition()
                        const newPoints = this.item.points().concat([pos.x ,pos.y])
                        this.item.points(newPoints)
                    }
                    this.lastStroke = this.color
                    this.layer.batchDraw();
                }
            },

            createPost(shape){
                let shapeData;
                if (shape== this.stage){
                    shapeData = {
                        id: 0,
                        shapeName : "container",
                        fill : this.stage.container().style.backgroundColor,
                    }
                    return shapeData
                }
                else if(shape.hasName('rectangle') || shape.hasName('square')){
                    shapeData = {
                        id: shape.id(),
                        shapeName : 'rectangle',
                        x : shape.x(),
                        y : shape.y(),
                        fill: shape.fill(),
                        stroke: shape.stroke(),
                        strokeWidth: shape.strokeWidth(),
                        draggable: shape.draggable(),
                        width : shape.width(),
                        height : shape.height(),
                    }
                }

                else if(shape.hasName('ellipse')){
                    shapeData = {
                        id: shape.id(),
                        shapeName : shape.name(),
                        x : shape.x(),
                        y : shape.y(),
                        fill: shape.fill(),
                        stroke: shape.stroke(),
                        strokeWidth: shape.strokeWidth(),
                        draggable: shape.draggable(),
                        radiusX : shape.radiusX(),
                        radiusY : shape.radiusY(),  
                    }
                }
                else if(shape.hasName('circle')){
                    shapeData = {
                        id: shape.id(),
                        shapeName : shape.name(),
                        x : shape.x(),
                        y : shape.y(),
                        fill: shape.fill(),
                        stroke: shape.stroke(),
                        strokeWidth: shape.strokeWidth(),
                        draggable: shape.draggable(),
                        radius : shape.radius(),  
                    }
                }
                else if(shape.hasName('triangle')){
                    shapeData = {
                        id: shape.id(),
                        shapeName : shape.name(),
                        x : shape.x(),
                        y : shape.y(),
                        fill: shape.fill(),
                        stroke: shape.stroke(),
                        strokeWidth: shape.strokeWidth(),
                        draggable: shape.draggable(),
                        sides : 3,
                        radius : 0,  
                    }
                }
                else if(shape.hasName('line')){
                    shapeData = {
                        id: shape.id(),
                        shapeName : shape.name(),
                        stroke: shape.stroke(),
                        strokeWidth: shape.strokeWidth(),
                        draggable: shape.draggable(),
                        points : shape.points(),
                        lineCap: 'round',
                        lineJoin: 'round',
                        
                    }
                }
                else if(shape.hasName('pencil')){
                    shapeData = {
                        id: shape.id(),
                        shapeName : 'pencil',
                        stroke: shape.stroke(),
                        strokeWidth: shape.strokeWidth(),
                        draggable: shape.draggable(),
                        points : shape.points(),
                        lineCap: 'round',
                        lineJoin: 'round',
                        
                    }
                }
                return shapeData
            },

            async fill(e){
                if(!this.isPosted[this.lastShape.id()]){
                    const response = await axios.post( this.url +'paint/create',this.createPost(this.shapes[this.lastShape.id()]))
                    this.isPosted[this.lastShape.id()] = true
                }
                if (e.target==this.stage){
                    this.stage.container().style.backgroundColor = this.color
                }
                else{
                    e.target.fill(this.color)
                }
                this.shapes[e.target.id()] = e.target
                console.log("testtttttttttt"+e.target.id())
                this.layer.batchDraw();
                
                const response = await axios.post( this.url +'paint/update?id='+ e.target.id() ,this.createPost(e.target))
            },

            async pick(e){
                if (e.target==this.stage){
                    this.emitter.emit("picked-color",{msg: this.stage.container().style.backgroundColor, slot:this.colorSlot++})
                }
                else{
                    this.emitter.emit("picked-color",{msg: e.target.fill(), slot:this.colorSlot++})
                }
            },

            async erase(e){
                if(e.target != this.stage){
                    if(!this.isPosted[this.lastShape.id()]){
                    const response = await axios.post( this.url +'paint/create',this.createPost(this.shapes[this.lastShape.id()]))
                    this.isPosted[this.lastShape.id()] = true
                     }
                    const response = await axios.post(this.url +'paint/delete?id='+ e.target.id() ,this.createPost(e.target))
                    this.shapes[e.target.id] = null
                    e.target.remove()
                }
            },
            
            async copy(e,ind){
                if(e.target != this.stage){
                    let copy;
                    if(e.target.hasName('rectangle')){
                        copy = new Konva.Rect({
                            x : e.target.x() - 75,
                            y : e.target.y() - 75,
                            width : e.target.width(),
                            height : e.target.height(),
                            name : 'rectangle',
                        });
                    }
                    else if(e.target.hasName('square')){
                        copy = new Konva.Rect({
                            x : e.target.x() - 75,
                            y : e.target.y() - 75,
                            width : e.target.width(),
                            height : e.target.height(),
                            name : 'square',
                        });
                    }
                    else if(e.target.hasName('ellipse')){
                        copy = new Konva.Ellipse({
                            radiusX : e.target.radiusX(),
                            radiusY : e.target.radiusY(),
                            x : e.target.x()- 75,
                            y : e.target.y()- 75,
                            name : 'ellipse',   
                        });
                    }
                    else if(e.target.hasName('circle')){
                        copy = new Konva.Circle({
                            radius : e.target.radius(),
                            x : e.target.x()- 75,
                            y : e.target.y()- 75,
                            name : 'circle',   
                        });
                    }
                    else if(e.target.hasName('triangle')){
                        copy = new Konva.RegularPolygon({
                            sides : e.target.sides(), //3
                            radius : e.target.radius(), //0
                            x : e.target.x()- 75,
                            y : e.target.y()- 75,
                            name : 'triangle',   
                        });
                    }
                    else if(e.target.hasName('line')){
                    copy = new Konva.Line({
                        points : [e.target.points()[0]-75, e.target.points()[1]-75, e.target.points()[2]-75, e.target.points()[3]-75],
                        lineCap: 'round',
                        lineJoin: 'round',
                        tension : e.target.tension(),
                        stroke : e.target.stroke(),
                        strokeWidth : e.target.strokeWidth(),
                        name : 'line',
                    });
                    }
                    else if(e.target.hasName('pencil')){
                        copy = new Konva.Line({
                            points : e.target.points().map(x => x-75),
                            lineCap: 'round',
                            lineJoin: 'round',
                            tension : e.target.tension(),
                            stroke : e.target.stroke(),
                            strokeWidth : e.target.strokeWidth(),
                            name : 'pencil',
                        });
                    }

                    copy.stroke(e.target.stroke())
                    copy.fill(e.target.fill())
                    copy.strokeWidth(e.target.strokeWidth())
                    copy.id(this.index++)
                    this.shapes.push(copy)
                    this.layer.add(copy)
                    this.layer.batchDraw()

                    if(!this.isPosted[this.lastShape.id()]){
                        const response = await axios.post( this.url +'paint/create',this.createPost(this.shapes[this.lastShape.id()]))
                        this.isPosted[this.lastShape.id()] = true
                    }
                    const response = await axios.post( this.url +'paint/create' ,this.createPost(e.target))
                }
            },

            Loading(obj){
                let name = obj.shapeName
                if (name=='container' || obj.id==0){
                    this.stage.container().style.backgroundColor = obj.fill
                }
                else{
                    if(name=='rectangle'||name=='square'){
                        this.item = new Konva.Rect({
                            width : obj.width,
                            height : obj.height,
                        });
                    }
                    else if(name=='circle'){
                        this.item = new Konva.Circle({
                            radius : obj.radius,
                        });
                    }
                    else if(name=='ellipse'){
                        this.item = new Konva.Ellipse({
                            radiusX : obj.radiusX,
                            radiusY : obj.radiusY,
                        });
                    }
                    else if(name=='triangle'){
                        this.item = new Konva.RegularPolygon({
                            radius : obj.radius,
                            sides : obj.sides,
                        });
                    }
                    else{
                        this.item = new Konva.Line({
                            points : obj.points,
                            lineCap: 'round',
                            lineJoin: 'round',
                        });
                    }
                    this.item.id(obj.id)
                    this.item.name(name)
                    this.item.x(obj.x)
                    this.item.y(obj.y)
                    this.item.stroke(obj.stroke)
                    this.item.strokeWidth(obj.strokeWidth)
                    this.item.fill(obj.fill)
                    this.item.draggable(false)
                    if (obj.id>this.index) this.index=obj.id
                    this.shapes.push(this.item)
                    this.layer.add(this.item).batchDraw()
                    this.isPosted[this.item] = true
                }
            },
         
            async moveResize(e){
                if(e.target.hasName('rectangle')||e.target.hasName('square')||e.target.hasName('ellipse')||e.target.hasName('circle')||e.target.hasName('line')||e.target.hasName('triangle')){  //if clicked on shape
                        if(!this.isPosted[this.lastShape.id()]){
                            const response = await axios.post( this.url +'paint/create',this.createPost(this.shapes[this.lastShape.id()]))
                            this.isPosted[this.lastShape.id()] = true
                        }
                        if(this.tr.nodes().length>0){
                            this.tr.nodes()[0].draggable(false) //remove draggable proprety from the previously selected shape
                        }
                        this.tr.nodes([])   //remove transformer from the previous one
                        this.layer.batchDraw()
                        if (this.tool=='select'){
                            if (e.target!=this.stage) e.target.draggable(true)
                        }
                        else if (this.tool=='resize'){
                            this.tr.nodes([e.target]) // add it to the transformer
                            this.layer.batchDraw()
                        }

                        console.log(e.target.id())
                        const response = await axios.post( this.url +'paint/update?id='+ e.target.id() ,this.createPost(e.target))
                    }
            },
            
            async undo(){
                
                if(!this.isPosted[this.lastShape.id()]){
                    const response = await axios.post( this.url +'paint/create',this.createPost(this.shapes[this.lastShape.id()]))
                    this.isPosted[this.shapes.length - 1] = true
                }
                this.shapes=[this.stage]
                this.isposted=[true]
                const response = await axios.get(this.url+'paint/undo')
                let obj = response.data
                for (var i=0 ;i< obj.length;i++){
                    this.Loading(obj[i]) 
                }       
            },

            async redo(){
                this.shapes=[this.stage]
                this.isposted=[true]
                const response = await axios.get(this.url+'paint/redo')
                let obj = response.data
                for (var i=0 ;i< obj.length;i++){
                    this.Loading(obj[i]) 
                }   
            }
    
        },

        mounted() {
            this.emitter.on("tool-clicked",(data) =>{
                this.tool = data.msg
                this.tr.nodes([])
                if (this.tool=='new'){
                    this.stage.destroyChildren()
                    this.stage.container().style.backgroundColor='white'
                    this.layer = new Konva.Layer();
                    this.stage.add(this.layer);
                    this.tr = new Konva.Transformer()
                    this.layer.add(this.tr);
                    this.index=1
                    this.shapes=[this.stage]
                }
                if(this.tool== 'undo'){
                    this.stage.destroyChildren()
                    this.layer = new Konva.Layer();
                    this.stage.add(this.layer);
                    this.tr = new Konva.Transformer()
                    this.layer.add(this.tr);
                    //this.index=1
                    this.undo()
                }
                if(this.tool== 'redo'){
                    this.stage.destroyChildren()
                    this.layer = new Konva.Layer();
                    this.stage.add(this.layer);
                    this.tr = new Konva.Transformer()
                    this.layer.add(this.tr);
                    //this.index=1
                    this.redo()
                }

            })
            this.emitter.on("load",(load) =>{
                this.stage.destroyChildren()
                this.stage.container().style.backgroundColor='white'
                this.layer = new Konva.Layer();
                this.stage.add(this.layer);
                this.tr = new Konva.Transformer()
                this.layer.add(this.tr);
                this.index=1
                this.shapes=[this.stage]
                for (var i=0;i<load.msg.length;i++){
                    this.Loading(load.msg[i])
                }
            })
            this.emitter.on("shape-clicked",(data) =>{
                this.shape = data.msg
                console.log(this.shape)
            })
            this.emitter.on("color-clicked",(data) =>{
                this.color = data.msg
                console.log(this.shape)
            })
            this.emitter.on("size-clicked",(data) =>{
                this.size = data.msg
                console.log(this.size)
            })

            this.stage = new Konva.Stage({
                container: this.$refs.container,
                width: window.innerWidth,
                height: window.innerHeight,
                id :0,
                name: 'container'
            });

            this.layer = new Konva.Layer();
            this.stage.add(this.layer);

            this.tr = new Konva.Transformer()
            this.layer.add(this.tr);
        
            this.stage.on("click",(e)=>{
    
                if (this.tool=='select'||this.tool=='resize'){
                    this.moveResize(e)
                    
                }
                else{
                    this.tr.nodes([])
                    if (e.target!=this.stage) e.target.draggable(false)
                }
                
                if(this.tool == 'copy'){
                    this.copy(e,this.index)
                    return;
                }
                this.shapes[e.target.id] = e.target
            });

            
            this.stage.on('mousedown', (e) => {

                if (e.target == this.stage) { // if touch the screen remove transformer
                    this.tr.nodes([]);
                    this.layer.batchDraw();
                }
                if (this.tool=='fill'){
                    this.fill(e)
                    return;
                }
                if (this.tool=='picker'){
                    this.pick(e)
                    return;
                }
                if(this.tool == 'eraser'){
                    this.erase(e)
                    return;
                }
            
                if (this.tool=='shape'||this.tool=='pencil'){
                    this.startDrawing(e);
                    this.stage.on('mouseup', this.endDrawing);
                    this.stage.on('mousemove', this.continueDrawing);
                }
                
                this.shapes[e.target.id] = e.target
            });
            
            
            this.layer.batchDraw()
        }, 
        
    }


</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
    h3 {
    margin: 40px 0 0;
    }
    ul {
    list-style-type: none;
    padding: 0;
    }
    li {
    display: inline-block;
    margin: 0 10px;
    }
    a {
    color: #42b983;
    }
</style>
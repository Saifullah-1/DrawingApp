<template>
    <div id="file-bar">
        <div class="dropdown">
            <button class="file-btn">File</button>
            <div class="dropdown-content">
                <button id="new-file" @click="tools('new')" ><img src="..\assets\pics\new-file-svgrepo-com.svg" style="vertical-align:middle">  New</button>
                <button for="myfile" id="open-file" @click="OpenFile('xml')"><img src="..\assets\pics\open-folder-svgrepo-com.svg" style="vertical-align:middle"> 
                    Open XML
                </button>
                <button for="myfile" id="open-file" @click="OpenFile('json')"><img src="..\assets\pics\open-folder-svgrepo-com.svg" style="vertical-align:middle"> 
                    <!-- <label for="myfile">Open</label> -->
                    <!-- <input type="file" name="myfile" id="myfile" style="opacity: .0001;"> -->
                    Open Json
                </button>
                <button id="save-file-dropdown" @click="SaveFile('xml')"><img src="..\assets\pics\save-svgrepo-com.svg" style="vertical-align:middle"> Save XML </button>
                <button id="save-file-dropdown" @click="SaveFile('json')"><img src="..\assets\pics\save-svgrepo-com.svg" style="vertical-align:middle"> Save Json</button>
            </div>
        </div>
        <div class="vertical-line">|</div>
        <div class="dropdown">
            <button class="file-btn" ref="copy" @click = "tools('copy')">Copy</button>
            <!-- <div class="dropdown-content"> -->
                <!-- <button id="cut-file"><img src="..\assets\pics\cut-svgrepo-com.svg" style="vertical-align:middle">  Cut</button> -->
                <!-- <button id="copy-file" ref="copy" @click = "tools('copy')"><img src="..\assets\pics\copy-document-svgrepo-com.svg" style="vertical-align:middle">  Copy</button> -->
                <!-- <button id="paste-file"><img src="..\assets\pics\paste-svgrepo-com.svg" style="vertical-align:middle">  Paste</button> -->
            <!-- </div> -->
        </div>
        
        <!-- <button id="save-file" style="text-align: center;"><img src="..\assets\pics\save-svgrepo-com.svg" style="vertical-align:middle"></button> -->
        <div class="vertical-line">|</div>
        <button id="undo" @click = "tools('undo')" style="text-align: center; "><img src="..\assets\pics\undo-left-svgrepo-com.svg" style="vertical-align:middle"></button>
        <button id="redo" @click = "tools('redo')" style="text-align: center; "><img src="..\assets\pics\undo-right-svgrepo-com.svg" style="vertical-align:middle"></button>
    </div>
    <div id="tools-bar" style="text-align: left;">
        <div id="tools" style="text-align: center;">
            <button id="eraser" ref="eraser" @click = "tools('eraser')"><img src="..\assets\pics\eraser-3-svgrepo-com.svg"></button>
            <button id="pencil" ref="pencil" class="hovered"  @click = "tools('pencil')"><img src="..\assets\pics\pencil-svgrepo-com.svg"></button>
            <button id="select" ref="select" @click = "tools('select')"><img src="..\assets\pics\pointer-svgrepo-com.svg"></button>
            <button id="resize" ref="resize" @click = "tools('resize')"><img src="..\assets\pics\resize-svgrepo-com.svg"></button>
            <button id="fill"   ref="fill"   @click = "tools('fill')" ><img src="..\assets\pics\fill-svgrepo-com.svg"></button>
            <button id="color-picker" ref="picker" @click="tools('picker')" ><img src="..\assets\pics\color-picker-dropper-colour-svgrepo-com.svg"></button>
            Tools
        </div>
        <div id="shapes" style="text-align: center;">
            <button id="line-segment" ref="line" @click = "tools('shape','line')"><img src="..\assets\pics\line-segment-svgrepo-com.svg"></button>
            <button id="circle" ref="circle" @click = "tools('shape','circle')"><img src="..\assets\pics\circle-svgrepo-com.svg"></button>
            <button id="ellipse" ref="ellipse" @click = "tools('shape','ellipse')"><img src="..\assets\pics\ellipse-figure-form-geometry-graphic-line-svgrepo-com.svg"></button>
            <button id="triangle" ref="triangle" @click = "tools('shape','triangle')"><img src="..\assets\pics\triangle-svgrepo-com.svg"></button>
            <button id="rectangle" ref="rectangle" @click = "tools('shape','rectangle')"><img src="..\assets\pics\rectangle-svgrepo-com.svg"></button>
            <button id="square" ref="square" @click = "tools('shape','square')"><img src="..\assets\pics\square-svgrepo-com.svg"></button>
            Shapes
        </div>
        <div id="colors" style="text-align: center;">
            <button id="basic-color" @click = "setcolor('#FFFFFF')"></button>
            <button id="basic-color" @click = "setcolor('#FF0000')"></button>
            <button id="basic-color" @click = "setcolor('#FFFF00')"></button>
            <button id="basic-color" @click = "setcolor('#008000')"></button>
            <button id="basic-color" @click = "setcolor('#0000FF')"></button>
            <button id="basic-color" @click = "setcolor('#8A2BE2')"></button>
            <button id="basic-color" @click = "setcolor('#000000')"></button>
            <button id="basic-color" @click = "setcolor('#808080')"></button>
            <button id="basic-color" @click = "setcolor('#A52A2A')"></button>
            <button id="basic-color" @click = "setcolor('#90EE90')"></button>
            <button id="basic-color" @click = "setcolor('#87CEFA')"></button>
            <button id="basic-color" @click = "setcolor('#FFC0CB')"></button>
            <button id="empty-color" ref="pos1" style="background-color:white;" @click="newColor('set',0)" ></button>
            <button id="empty-color" ref="pos2" style="background-color:white;" @click="newColor('set',1)" ></button>
            <button id="empty-color" ref="pos3" style="background-color:white;" @click="newColor('set',2)"></button>
            <button id="empty-color" ref="pos4" style="background-color:white;" @click="newColor('set',3)"></button>
            <input type="color" ref="palette" id="gradient" value="#000000" @change="PaletteSetcolor"> <br>
            Colors
        </div>
        <div id="slider" style="text-align: center;">
            <div style="height: 10px; margin-top: 27px;"><input type="range" min="1" max="100" class="slider" ref="sizeSlider"  value="2" @change='Size'></div> <br><br>
            Size = {{ this.size }}px
        </div>
    </div>
    </template>
    
    <script>
    import axios from 'axios'
    export default {
    data(){
        return{
            color : '#000000',
            size : 2,
            Tool : true,
            clickedFill : false,
            clickedDraw : true,
            clickedErase : false,
            clickedPicker : false,
            clickedMove : false,
            clickedSelect : false,
            clickedShape : false,
            clickedResize : false,
            canRedo : false,
            File:null,
            loaded : [],
            //type: ''
        }
    },
    props(){
    },
    methods :{
    
        async SaveFile(type){
           // this.type = typ
            this.data = await axios.get("http://localhost:8080/paint/save?fileType="+type);
        },

        async Save(){
            // let stream = await fileHandle.createWritable()
            // await stream.write('ggggggg')
            // await stream.close()
        },

        async OpenFile(type){
        //     await window.showOpenFilePicker({types:[
        //         {
        //             description : 'Paint',
        //             accept :{
        //                 'Paint/*' : ['.xml', '.json'] 
        //             }
        //         },
        //     ],
        //     excludeAcceptAllOption:true,
        //     multiple:false
        // })
        //     this.File = await FileSystemHandle.getFile()
        //     // this.File = await FileHandle .getFile()
            // const response = fetch("http://localhost:3000/shapes")
            // // this.data = await axios.get("http://localhost:8080/paint/load?fileType=json");
            // this.data = await response.json();
            // console.log(JSON.stringify(this.data))
            // // this.loaded = []
            // // this.loaded=this.data.parse()
            // // this.emitter.emit("load",{msg: this.loaded})

            // const response = await fetch("http://localhost:3000/shapes");
            // this.data = JSON.stringify(response);
            const response = await axios.get("http://localhost:8080/paint/load?fileType="+type)
            let obj = response.data
                // for (var i=0;i<data.length;i++){
                    // console.log (data[i])
            this.emitter.emit("load",{msg: obj})
                // }
            

            // console.log ("Length Before Send = " ,this.loaded.length )
            // for (var i=0;i<this.loaded.length;i++){
            //     console.log(this.loaded[i].id)
            //     this.emitter.emit("load",{msg: this.loaded[i]})
            // }
            // this.emitter.emit("load",{msg: this.loaded})
        },

        unclick(){
            this.clickedFill = false
            this.clickedDraw = false
            this.clickedErase = false
            this.clickedMove = false
            this.clickedSelect = false
            this.clickedShape = false
            this.clickedPicker = false
            this.clickedResize = false
            this.Tool= false
            this.$refs.line.classList.remove('hovered') 
            this.$refs.circle.classList.remove('hovered') 
            this.$refs.ellipse.classList.remove('hovered') 
            this.$refs.rectangle.classList.remove('hovered') 
            this.$refs.square.classList.remove('hovered') 
            this.$refs.triangle.classList.remove('hovered') 
            this.$refs.eraser.classList.remove('hovered') 
            this.$refs.pencil.classList.remove('hovered') 
            this.$refs.fill.classList.remove('hovered') 
            this.$refs.picker.classList.remove('hovered') 
            this.$refs.select.classList.remove('hovered') 
            this.$refs.resize.classList.remove('hovered') 
        },
    
        click (element,shape){
            this.Tool = true
            this.canRedo = false
            switch (element){
                case 'pencil': {
                    this.$refs.pencil.className='hovered'
                    this.clickedDraw = true
                    break
                }
                case 'eraser':{
                    this.$refs.eraser.className='hovered'
                    this.clickedErase = true
                    break
                }
                case 'fill':{
                    this.$refs.fill.className='hovered'
                    this.clickedFill = true
                    break
                }
                case 'picker':{
                    this.$refs.picker.className='hovered'
                    this.clickedPicker = true
                    break
                }
                case 'select': {
                    this.$refs.select.className='hovered'
                    this.clickedSelect = true
                    break
                }
                case 'resize':{
                    this.$refs.resize.className='hovered'
                    this.clickedResize = true
                    break
                }
                case 'fill':{
                    this.$refs.fill.className='hovered'
                    this.clickedFill = true
                    break
                }
                default : break
            }
            if (element=='shape'){
                this.clickedShape = true
                switch(shape){
                    case 'circle': {
                        this.$refs.circle.className='hovered'
                        break
                    }
                    case 'ellipse': {
                        this.$refs.ellipse.className='hovered'
                        break
                    }
                    case 'square': {
                        this.$refs.square.className='hovered'
                        break
                    }
                    case 'rectangle' :{
                        this.$refs.rectangle.className='hovered'
                        break
                    }
                    case 'triangle': {
                        this.$refs.triangle.className='hovered'
                        break
                    }
                    case 'line': {
                        this.$refs.line.className='hovered'
                        break
                    }
                    default : break
                }
            }
        },
    
        setcolor (val){
            this.color = val
            this.$refs.palette.value=val
            this.emitter.emit("color-clicked",{msg: this.color})
            console.log(val,this.$refs.palette.value)
        },
    
        PaletteSetcolor (){
            this.color=this.$refs.palette.value
            this.emitter.emit("color-clicked",{msg: this.color})
        },
    
        newColor (order,ind,color='white'){
            ind = ind%4
            if (order=='add'){
                if (ind==0) this.$refs.pos1.style.backgroundColor=color
                else if (ind==1) this.$refs.pos2.style.backgroundColor=color
                else if (ind==2) this.$refs.pos3.style.backgroundColor=color
                else if (ind==3) this.$refs.pos4.style.backgroundColor=color
            }
            else if (order=='set'){
                if (ind==0) color=this.$refs.pos1.style.backgroundColor
                else if (ind==1) color=this.$refs.pos2.style.backgroundColor
                else if (ind==2) color=this.$refs.pos3.style.backgroundColor
                else if (ind==3) color=this.$refs.pos4.style.backgroundColor
                console.log(color)
                if (color == 'white' || color =='#ffffff') color = 'rgb(255, 255, 255)'
                if (color[0]=='r'||color[0]=='R') color = this.toHex(color)
                // this.setcolor(this.toHex(color))
                this.setcolor(color)
            }
        },
    
        toHex (str){
            console.log(str)
            let i=0
            while (!(str[i]==+str[i])) i++
            let r=''
            let g=''
            let b=''
            while ((str[i]==+str[i])){
                r+=str[i]
                i++
            }
            i+=2
            while ((str[i]==+str[i])){
                g+=str[i]
                i++
            }
            i+=2
            while ((str[i]==+str[i])){
                b+=str[i]
                i++
            }
            r = this.Hex(+r)
            g = this.Hex(+g)
            b = this.Hex(+b)
            console.log(r,g,b)
            let res = '#'+r.toString()+g.toString()+b.toString()
            return (res)
        },
        Hex(a){
                let r=''
                let m = a%16
                let arr = ['a','b','c','d','e','f']
                a=Math.floor(a/16)
                if (a>=10) a = arr[a-10]
                r+=a
                if (m>=10) m = arr[m-10]
                r+=m
                return r
        },
    
        Size(){
            this.size = this.$refs.sizeSlider.value
            this.emitter.emit("size-clicked",{msg: this.size})
        },
        
        tools (item ,shape ='NO'){
            let bool = null
            let hover = true
            if (item=='shape') bool=this.clickedShape
            else if (item=='pencil') bool=this.clickedDraw
            else if (item=='eraser') bool=this.clickedErase
            else if (item=='fill') bool=this.clickedFill
            else if (item=='move') bool=this.clickedMove
            else if (item=='picker') bool=this.clickedPicker
            else if (item=='select') bool=this.clickedSelect
            else if (item=='resize') bool=this.clickedResize
            else if (item=='new') fetch("http://localhost:8080/paint/new")
            if (shape !='NO'){
                if (shape=='circle'&&this.$refs.circle.className != 'hovered') hover=false  
                else if (shape=='rectangle'&&this.$refs.rectangle.className != 'hovered') hover=false  
                else if (shape=='triangle'&&this.$refs.triangle.className != 'hovered') hover=false  
                else if (shape=='square'&&this.$refs.square.className != 'hovered') hover=false  
                else if (shape=='ellipse'&&this.$refs.ellipse.className != 'hovered') hover=false  
                else if (shape=='line'&&this.$refs.line.className != 'hovered') hover=false  
            }
            this.emitter.emit("tool-clicked",{msg: item})
            if (this.Tool&&bool&&hover){
            this.unclick()
            this.tools('select')
            }
            else{
                this.unclick()
                this.click(item,shape)
            }
            if (shape !='NO'){
                this.emitter.emit("shape-clicked",{msg: shape})
            }
            
        },
    
        /*
        function Save(){
            // Send to the backend to prepare and store the json file
            // Message that the file has been saved with the path
        }*/
    
        // async fetchData() {      
        // this.data = await axios.get("try.json");
        // console.log (this.data)
        // }
    
        // function Undo(){
        //     // Send to the backend to send the top of the stack
        //     // perform the undo
        //     canRedo = true
        //     // no hover
        // }
    
        // function Redo(){
        //     if (canRedo){
        //         // Send to the backend to return what to be redo
        //     }
        //     // no hover
        // }
    // */
    },
    mounted() {
        this.emitter.on("picked-color",(data) =>{
            this.extraColor = data.msg
            this.slot = data.slot
            this.newColor('add',this.slot,this.extraColor)
        })
        
    },  
    }
    </script>
    
    <style>
    #file-bar{
    height: 40px;
    width: 100%;
    background-color: azure;
    }
    
    .dropdown-content{
    display: none;
    position: absolute;
    background-color:azure;
    box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
    }
    
    .dropdown-content-save{
    display: none;
    position: absolute;
    background-color:azure;
    box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
    }
    
    .dropdown-content button{
    height: 40px;
    width: 100px;
    border: none;
    background-color: azure;
    text-align: left;
    }
    
    .dropdown{
    position: relative;
    display: inline-block;
    z-index: 5;
    }
    
    .dropdown:hover .dropdown-content{
    display: block;
    }

    .dropdown:hover .file-btn{
    background-color: rgb(206, 249, 249);
    }
    
    .dropdown-content button:hover{
    background-color: rgb(206, 249, 249);
    }
    
    .file-btn{
    background-color: azure;
    height: 40px;
    width: 50px;
    border: none;
    font-size: 17px;
    font-weight: 500;
    text-align: center;
    }
    
    .vertical-line{
    position: relative;
    display: inline-block;
    background-color: azure;
    height: 20px;
    width: 10px;
    border: none;
    font-size: 25px;
    font-weight: 500;
    -webkit-text-fill-color: rgb(225, 225, 225);
    text-align: center;
    }
    
    #save-file{
    height: 35px;
    width: 40px;
    border: none;
    background-color: azure;
    text-align: left;
    }
    
    #save-file:hover{
    border-radius: 5px;
    border: 2px solid ;
    background-color: rgb(206, 249, 249);
    }
    
    #undo{
    height: 35px;
    width: 40px;
    border: none;
    background-color: azure;
    text-align: left;
    }
    
    #undo:hover{
    border-radius: 5px;
    border: 2px solid ;
    background-color: rgb(206, 249, 249);
    }
    
    #redo{
    height: 35px;
    width: 40px;
    border: none;
    background-color: azure;
    text-align: left;
    }
    
    #redo:hover{
    border-radius: 5px;
    border: 2px solid ;
    background-color: rgb(206, 249, 249);
    }
    
    /*tools bar*/
    #tools-bar{
    height: 100px;
    width: 100%;
    background-color: azure;
    align-items: center;
    border-bottom: #1cd2d2 solid 2px;
    }
    
    #tools{
    display: inline-block;
    height: 100px;
    width: 120px;
    border-right: 2px solid rgb(225, 225, 225);
    }
    
    #tools button{
    background-color: azure;
    height: 35px;
    width: 35px;
    border: 2px solid azure;
    font-size: 17px;
    font-weight: 500;
    }
    
    #tools button:hover{
    border-radius: 5px;
    border: 2px solid black;
    background-color: rgb(206, 249, 249);
    }
    
    #shapes{
    display: inline-block;
    vertical-align: top;
    height: 100px;
    width: 120px;
    border-right: 2px solid rgb(225, 225, 225);
    }
    
    #shapes button{
    background-color: azure;
    height: 35px;
    width: 35px;
    border: 2px solid azure;
    font-size: 17px;
    font-weight: 500;
    }
    
    #shapes button:hover{
    border-radius: 5px;
    border: 2px solid black;
    background-color: rgb(206, 249, 249);
    }
    
    .hovered {
    border-radius: 5px !important;
    border: 2px solid black !important;
    background-color: rgb(206, 249, 249) !important;
    }
    
    #colors{
    display: inline-block;
    vertical-align: top;
    height: 100px;
    width: 200px;
    border-right: 2px solid rgb(225, 225, 225);
    }

    
    #slider{
    display: inline-block;
    vertical-align: top;
    height: 100px;
    width: 120px;
    border-right: 2px solid rgb(225, 225, 225);
    }
    
    #colors button{
    margin-top: 2px;
    height: 19px;
    width: 19px;
    border-radius: 50%;
    border: 1px solid rgb(88, 88, 88);
    margin: 2px 2px 0px
    }
    
    #empty-color{
    background-color: white;
    height: 18px;
    width: 18px;
    border-radius: 50%;
    border: 1px solid rgb(88, 88, 88);
    
    }
    
    #gradient{
    width: 50px;
    height: 28px;
    background-color: azure;
    border: none;
    }

    
    #colors button:nth-child(1){
    background-color: white;
    }
    
    #colors button:nth-child(2){
    background-color: red;
    }
    
    #colors button:nth-child(3){
    background-color: yellow;
    }
    
    #colors button:nth-child(4){
    background-color: green;
    }
    
    #colors button:nth-child(5){
    background-color: blue;
    }
    
    #colors button:nth-child(6){
    background-color: blueviolet;
    }
    
    #colors button:nth-child(7){
    background-color: black;
    }
    
    #colors button:nth-child(8){
    background-color: gray;
    }
    
    #colors button:nth-child(9){
    background-color: brown;
    }
    
    #colors button:nth-child(10){
    background-color: lightgreen;
    }
    
    #colors button:nth-child(11){
    background-color: lightskyblue;
    }
    
    #colors button:nth-child(12){
    background-color: pink;
    }

    @media screen and (-webkit-min-device-pixel-ratio:0) {
input[type='range'] {
    overflow: hidden;
    -webkit-appearance: none;
    background-color: azure;
    padding : 3px 0px;
    width: 90%;
    height: 0px;
    opacity: 0.7; 
    border : black solid 2px;
    border-radius: 8px;
    -webkit-transition: .2s;
    transition: opacity .2s;
    }
    
    input[type='range']::-webkit-slider-runnable-track {
    height: 20px;
    -webkit-appearance: none;
    }
    
    input[type='range']::-webkit-slider-thumb {
    width: 5px;
    -webkit-appearance: none;
    height: 100%;
    cursor: ew-resize;
    background: rgb(0, 0, 0);
    box-shadow: -80px 0 0 80px #68adf2;
    }

    input[type='range']:hover {
    height: 10px;
    -webkit-appearance: none;
    cursor: ew-resize;
    background: #434343;
    }
        
    }
    .slider:hover {
        opacity: 1; 
    }
    </style>
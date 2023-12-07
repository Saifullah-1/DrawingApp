<template>
    <div id="file-bar">
        <div class="dropdown">
            <button class="file-btn">File</button>
            <div class="dropdown-content">
                <button id="new-file"><img src="..\assets\pics\new-file-svgrepo-com.svg" style="vertical-align:middle">  New</button>
                <button id="open-file"><img src="..\assets\pics\open-folder-svgrepo-com.svg" style="vertical-align:middle">  Open</button>
                <button id="save-file-dropdown"><img src="..\assets\pics\save-svgrepo-com.svg" style="vertical-align:middle">  Save</button>
            </div>
        </div>
        <div class="dropdown">
            <button class="file-btn">Edit</button>
            <div class="dropdown-content">
                <button id="cut-file"><img src="..\assets\pics\cut-svgrepo-com.svg" style="vertical-align:middle">  Cut</button>
                <button id="copy-file"><img src="..\assets\pics\copy-document-svgrepo-com.svg" style="vertical-align:middle">  Copy</button>
                <button id="paste-file"><img src="..\assets\pics\paste-svgrepo-com.svg" style="vertical-align:middle">  Paste</button>
            </div>
        </div>
        <div class="vertical-line">|</div>
        <button id="save-file" style="text-align: center;"><img src="..\assets\pics\save-svgrepo-com.svg" style="vertical-align:middle"></button>
        <div class="vertical-line">|</div>
        <button id="undo" style="text-align: center;"><img src="..\assets\pics\undo-left-svgrepo-com.svg" style="vertical-align:middle"></button>
        <button id="redo" style="text-align: center;"><img src="..\assets\pics\undo-right-svgrepo-com.svg" style="vertical-align:middle"></button>
    </div>
    <div id="tools-bar" style="text-align: left;">
        <div id="tools" style="text-align: center;">
            <button id="eraser" ref="eraser"><img src="..\assets\pics\eraser-3-svgrepo-com.svg"></button>
            <button id="pencil" class="hovered" ref="pencil" @click = "tools('pencil')"><img src="..\assets\pics\pencil-svgrepo-com.svg"></button>
            <button id="select" ref="select" @click = "tools('select')"><img src="..\assets\pics\pointer-svgrepo-com.svg"></button>
            <button id="resize" ref="resize" @click = "tools('resize')"><img src="..\assets\pics\resize-svgrepo-com.svg"></button>
            <button id="fill" ref="fill" ><img src="..\assets\pics\fill-svgrepo-com.svg"></button>
            <button id="color-picker" ref="picker"><img src="..\assets\pics\color-picker-dropper-colour-svgrepo-com.svg"></button>
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
            <button id="empty-color"></button>
            <button id="empty-color"></button>
            <button id="empty-color"></button>
            <button id="empty-color"></button>
            <input type="color" ref="palette" id="gradient" value="#000000" @change="PaletteSetcolor"><br>
            Colors
        </div>
    </div>
</template>

<script>
export default {
    data(){
        return{
            array : [],
            refs : [],
            names : [],
            bools : [],
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
        }
    },
    props(){
    },
    methods :{

        unclick(){
            this.Tool= false
            let refs = this.refsArray('get')
            for (var i=0;i<refs[1].length;i++){
                if (i<refs[2].length) refs[2][i]=false
                refs[0][i].classList.remove('hovered')
            }
            this.refsArray('update',refs)
        },

        click (element,shape){
            this.Tool = true
            this.canRedo = false
            let refs = this.refsArray('get')
            if (element!='shape'){
                for (var i=0;i<refs[1].length-6;i++){
                    if (refs[1][i]==element){
                        refs[0][i].className='hovered'
                        refs[2][i]=true
                    }
                }
            }
            else{
                refs[2][refs[2].length-1]=true
                for (var i=refs[1].length-6;i<refs[1].length;i++){
                    if (refs[1][i]==shape){
                        refs[0][i].className='hovered'
                    }
                }
            }
            console.log ("Is it????",refs[2][6])
            this.refsArray('update',refs)
            console.log ("Is it?",this.clickedShape)

        },
        
        setcolor (val){
            this.color = val
            this.$refs.palette.value=val
            this.emitter.emit("color-clicked",{msg: this.color})
        },

        PaletteSetcolor (){
            console.log("Entered")
            this.color=this.$refs.palette.value
            this.emitter.emit("color-clicked",{msg: this.color})
        },

        Size(){
            //Get the Size of the color element in html 
            //color = colorChoosen
        },
        
        tools (item ,shape ='NO'){
            let bool = null
            let hover = true
            let refs = this.refsArray('get')
            console.log ("Before",refs[2][6])
            console.log ("Is it",this.clickedShape)
            if (item=='shape') {
                bool=refs[2][refs[2].length-1]
                console.log ("after",refs[2][6])
            }
            else{
                for (var i=0;i<refs[1].length-6;i++){
                    if (refs[1][i]==item){
                        bool = refs[2][i]
                    }
                }
            }if (shape !='NO'){
                for (var i=refs[1].length-6;i<refs[1].length;i++){
                    if (refs[1][i]==shape&&refs[0][i].className != 'hovered'){
                        hover=false
                    }
                }
            }
            this.emitter.emit("tool-clicked",{msg: item})
            console.log ("Hovered : ",hover , " , Its Boolen : " ,bool, " , Tool : ", this.Tool)
            console.log ("booooooooooooooool",this.array[2][6])

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
            this.refsArray('update',refs)
        },

        /*
        function Save(){
            // Send to the backend to prepare and store the json file
            // Message that the file has been saved with the path
        }

        function Load(){
            // Send to the backend to prepare and send the json file
            // Check the path to load from
            // Message that the file has been Loaded 
        }

        function Undo(){
            // Send to the backend to send the top of the stack
            // perform the undo
            canRedo = true
            // no hover
        }

        function Redo(){
            if (canRedo){
                // Send to the backend to return what to be redo
            }
            // no hover
        }
*/
        refsArray(req,arr){
            if (req='get'){
                this.array=[]
                this.refs=[]
                this.names=[]
                this.bools=[]
                this.refs.push(this.$refs.eraser,this.$refs.pencil,this.$refs.fill)
                this.refs.push(this.$refs.picker,this.$refs.select,this.$refs.resize)
                this.refs.push(this.$refs.line,this.$refs.circle,this.$refs.ellipse)
                this.refs.push(this.$refs.triangle,this.$refs.square,this.$refs.rectangle)
                this.names.push('eraser','pencil','fill','picker','select','resize')
                this.names.push('line','circle','ellipse','triangle','square','rectangle')
                this.bools.push(this.clickedErase,this.clickedDraw,this.clickedFill)
                this.bools.push(this.clickedPicker,this.clickedSelect,this.clickedResize)
                this.bools.push(this.clickedShape)
                this.array.push(this.refs,this.names,this.bools)
                return this.array
            }
            else if (req=='update'){
                this.clickedErase = arr[2][0]
                this.this.clickedDraw = arr[2][1]
                this.clickedFill = arr[2][2]
                this.clickedPicker = arr[2][3]
                this.clickedSelect = arr[2][4]
                this.clickedResize = arr[2][5]
                this.clickedShape = arr[2][6]
            }
            
        },


    } 
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

#colors button{
margin-top: 2px;
height: 19px;
width: 19px;
border-radius: 50%;
border: 1px solid rgb(88, 88, 88);
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
</style>
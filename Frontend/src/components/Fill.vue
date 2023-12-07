<template>
    <div ref="container" style="background-color: white"></div>
</template>

<script>
export default {
    data (){
        return {
            wid : 0,
            hig : 0,
        }
    },
    methods:{
        startDrawing(){
            let c = new Konva.Circle({
                radius : 15,
                x : 100,
                y : 100,
                stroke : 'black',
                strokeWidth : 3,
                draggable : true,
            });
            let r = new Konva.Rect({
                x : 200,
                y : 100,
                stroke : 'black',
                strokeWidth : 3,
                width :150,
                height : 150,
                fill : '#99b0c7',
                draggable : true,
            });
            this.layer.add(r).batchDraw()
            this.layer.add(c).batchDraw()
        },
        copy (e){
            let copy = new Konva.Rect({
                x : this.stage.getPointerPosition().x - 75,
                y : this.stage.getPointerPosition().y - 75,
                stroke : 'black',
                strokeWidth : 3,
                width :150,
                height : 150,
                fill : '#99b0c7',
                draggable : true,
            })
        this.layer.add(copy)
        this.layer.batchDraw()
        }
    },
    mounted() {
        this.stage = new Konva.Stage({
        container: this.$refs.container,
        width: window.innerWidth,
        height: window.innerHeight,
        });
        this.layer = new Konva.Layer();
        this.stage.add(this.layer);
        this.startDrawing()
        // this.stage.on('mousemove',(e)=>{
        //     this.copy(e)

        // })
        this.stage.on('click',(e)=>{
            this.copy(e)
            this.layer.batchDraw()
        })
        
    }
}
</script>

<style>
    div{
        height:5000px;
        width:5000px;
    }
</style>
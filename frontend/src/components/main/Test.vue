<template>
    <div class="test-wrapper">

        <h2>Image File Upload Test-View</h2>

        <div class="imageUpload-wrapper">
            <p>이미지 파일 업로드</p>

            <div v-if="imageComputed">
                <input
                        style="display: none"
                        type="file"
                        @change="onFileSelected"
                        ref="fileInput">
                <div>
                    <button class="select-btn" @click="$refs.fileInput.click()">파일선택</button>
                </div>
            </div>
            <div v-else>
                <button class="remove-btn" @click="removeImage">이미지 삭제</button>
                <button class="upload-btn" @click="onUpload">이미지 업로드</button>

                <br>
                <br>
                <br>

                <v-avatar
                        :tile="tile"
                        size=250px
                >
                    <img :src="image" alt="avatar"/>
                </v-avatar>
            </div>
        </div>
    </div>
</template>

<script>

    export default {
        name: "Test",
        data() {
            return {
                tile: false,
                selectedFile: null,
                image: '',
            }
        },
        computed: {
            imageComputed(){
                if (this.image.length === 0){
                    return true;
                }

                return false;
            }
        },
        methods: {
            onFileSelected(event) {
                this.selectedFile = event.target.files[0];

                if (this.selectedFile.length === 0) {
                    return;
                }

                this.createImage();
            },

            createImage() {
                this.image = new Image();
                let reader = new FileReader();

                reader.readAsDataURL(this.selectedFile);

                reader.onload = (()=>{
                    this.image = reader.result;
                });

            },

            removeImage(){
                this.image = '';
            },

            onUpload() {

                let formData = new FormData();
                formData.append('image', this.selectedFile, this.selectedFile.name);

                let uploadUrl = "http://localhost:8080/api/users/image/2"
                let config = {
                    headers: {
                        'Content-type': 'multipart/form-data'
                    }
                };

                this.$http.post(uploadUrl, formData, config).then((response) => {
                    console.log("success");
                    this.image = '';
                }).catch((e) => {
                    console.log("fail");
                });
            }
        }
    }
</script>

<style scoped>

    .test-wrapper {
        width: 800px;
        margin:50px auto;
        text-align: center;
    }

    .imageUpload-wrapper {
        margin-top:20px;
        padding-top: 20px;
        padding-bottom: 20px;
        border: 1px solid grey;
    }

    .select-btn {
        border: 1px solid cadetblue;
        padding: 5px;
        margin-right: 6px;
    }

    .select-btn:hover {
        background-color: cadetblue;
        color: white;
    }

    .upload-btn {
        border: 1px solid cadetblue;
        padding: 5px;
        margin-left: 6px;
    }

    .upload-btn:hover {
        background-color: cadetblue;
        color: white;
    }

    .remove-btn {
        border: 1px solid cadetblue;
        padding: 5px;
    }

    .remove-btn:hover {
        background-color: cadetblue;
        color: white;
    }

</style>
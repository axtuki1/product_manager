<template>
    <div class="notfound-wrapper">
        <div class="notfound">
            <div class="title">Not Found</div>
            <div class="reason">
                お探しのページは見つかりませんでした。<br>
                URL、送信データ等を確認してください。
            </div>
        </div>
    </div>
</template>

<script>
module.exports = {
    
}
</script>

<style scoped>

.notfound-wrapper  {
    display: flex;
    justify-content: center;
    align-items: center;
}

.notfound { 
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
}

.title {
    font-size: 6rem;
    font-weight: bold;
    border-bottom: 1px solid #000;
    padding: 0 70px;
}

.reason {
    margin-top: 40px;
}

</style>
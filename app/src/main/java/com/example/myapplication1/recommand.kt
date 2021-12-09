package com.example.myapplication1

class recommand{
    fun rec_list(id:Int, tag:String): ArrayList<Int> {
        var boughtlist = arrayListOf<Int>()
        var list = arrayListOf<Int>()
        val num = 0
        if (boughtlist.contains(num)==false)
            list.add(num)
        return list
    }
    fun recommand(id:Int): Pair<String, String> {
        var boughtlist = arrayListOf<Int>()
        var tag = arrayListOf<String>()
        var taglist = arrayListOf<String>()
        var tagnum = arrayListOf<Int>()
        var first_tag : String = ""
        var second_tag : String = ""
        var max:Int = 0
        for (item in boughtlist){
            tag.add("")
            for (item in tag)
            {
                if(taglist.contains(item)==false)
                {
                    taglist.add(item)
                    tagnum.add(0)
                }
                else
                {
                    val num = taglist.indexOf(item)
                    tagnum[num]++
                }
            }
        }
        for (i in tagnum.indices)
        {
            if(tagnum[i]>max)
            {
                max = tagnum[i]
                second_tag = first_tag
                first_tag = taglist[i]
            }
        }
        return Pair(first_tag, second_tag)
    }
}
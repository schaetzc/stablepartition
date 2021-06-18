This is an (unfinished) implementation of the paper [Stable Minimum Space Partitioning in Linear Time](http://hjemmesider.diku.dk/~jyrki/Paper/KP1992bJ.pdf) by Yrki Katajainen and Tomi Pasanen. Their algorithm stably¹ partitions² an array of length *n* in *O(n)* time and *O(1)* extra space.

<sub>² to partition = Sort an array by a predicate. For instance `3, 6, 4, 1, 5` partitioned by the predicate `isEven` is `3, 1, 5;` `6, 4`.  
¹ stable = Elements with the same result from the predicate appear in the same order after sorting.</sub>

Apart from the actual implementation this github repository also serves as a place to discuss the theoretical details of this algorithm. Have a look at the [issues tagged as *theory*](https://github.com/schaetzc/Stable-Partition/labels/theory). 

### Participate

You want to help complete this implementation? Great!
First read the papers:

- [Stable Minimum Space Partitioning in Linear Time](http://hjemmesider.diku.dk/~jyrki/Paper/KP1992bJ.pdf) by Yrki Katajainen and Tomi Pasanen
- [Fast Stable In-Place Sorting with O(n) Data Moves](https://www.researchgate.net/publication/226451719_Fast_stable_in-place_sorting_withOn_data_moves) by J. I. Munro and V. Raman
- [Stable In-Situ Sorting and Minimum Data Movement](https://link.springer.com/article/10.1007%2FBF02017344) by J. Ian Munro, Venkatesh Raman, and Jeffrey S. Salowe

To discuss theoretical details about these papers open an issue with the tag *theory*.
Once you have a good grasp of the part you want to implement, start coding and send a pull request. You can also ask to be added as a contributor by opening an issue.


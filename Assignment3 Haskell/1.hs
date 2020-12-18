import Data.List

-- Get if a set is empty or not by checking size of the set
is_null :: [Int] -> Bool
is_null set = length set == 0

--Take set union here we are taking set union as A U B = {A-B , B}
set_union :: [Int] -> [Int] -> [Int]
set_union set1 set2 = [x | x <- set1, x `notElem` set2]++set2

-- Set Intersection = A intersection B = set of values both in A and B
set_intersection :: [Int] -> [Int] -> [Int]
set_intersection set1 set2 = [x | x <- set1, x `elem` set2]


-- Set Subtraction Removing elements from set A which are also in Set B
set_subtraction :: [Int]->[Int]->[Int]
set_subtraction set1 set2 = [x | x <- set1,  x `notElem` set2]


-- Function to remove duplicates in the Set/List
remove_duplicates :: [Int]->[Int]->[Int]
remove_duplicates [] list2 = list2
remove_duplicates (x:list1) list2 = if x `notElem` list2 then do
                                     let temp = [x]++list2
                                     remove_duplicates list1 temp
                                  else do
                                     remove_duplicates list1 list2


-- Addition of sets. First take addition of each element in set to to each element in B and the  remove duplicates from the resulting set
set_addition :: [Int]->[Int]->[Int]
set_addition set1 set2 = remove_duplicates ([x+y | x <- set1, y <- set2]) []




import Data.Function
import Data.List 
import Data.Maybe


-- Recursive solution to get room size
-- This function first check if all the paths/ room types are evaluated or not
-- It creates two designs first is by increasing dimensions of current location type and other is by chnaging room type.
checkRoom :: Int->[Int]->[[Int]]->[[Int]]->Int->[[Int]]
checkRoom room_type room_counts room_sizes max_room_sizes space = if room_type >= 6 then do is_valid_design room_counts room_sizes space else do 
           if ((room_sizes!!room_type))!!0 <= (max_room_sizes!!room_type)!!0 && ((room_sizes!!room_type))!!1 <= (max_room_sizes!!room_type)!!1 then do
                  -- increase the size of the current component
                  let des1 = checkRoom room_type room_counts ( (take (room_type) room_sizes) ++ [[(((room_sizes!!room_type)!!0) + 1),(((room_sizes!!room_type)!!1) + 1)]] ++ (drop (room_type+1) room_sizes)) max_room_sizes space 
                  -- Move to next Component
                  let des2 = checkRoom  (room_type+1) room_counts room_sizes max_room_sizes space
                  -- Select Best Design
                  best_design des1 des2 room_counts
           else
                  [ [0, 0], [0, 0], [0, 0], [0, 0], [0, 0], [0, 0] ]
                  
                  



-- This function check if a designed solution is valid or not in terms of the area of various rooms/balcon/garden
is_valid_design :: [Int]->[[Int]]->Int->[[Int]]
is_valid_design room_counts room_sizes space = do
    let t_area = total_area room_counts room_sizes    --total_area covered by all designed places
    let kitchen_area = room_area 2 room_sizes         -- kitchen area
    let hall_area = room_area 1 room_sizes            -- hall area
    let bathroom_area = room_area 3 room_sizes        -- bathroom area
    let bedroom_area = room_area 0 room_sizes 
    -- total space should be less than given space
    if(t_area > space) then
          [ [0, 0], [0, 0], [0, 0], [0, 0], [0, 0], [0, 0] ]
     else if (kitchen_area>hall_area) then                    -- kitchen area<=hall_area
         [ [0, 0], [0, 0], [0, 0], [0, 0], [0, 0], [0, 0] ]   -- kitchen area <= bedroom area
     else if (kitchen_area >  bedroom_area) then 
          [ [0, 0], [0, 0], [0, 0], [0, 0], [0, 0], [0, 0] ]
     else if(bathroom_area > kitchen_area) then                -- bathroom area <= kitchen area
          [ [0, 0], [0, 0], [0, 0], [0, 0], [0, 0], [0, 0] ]
     else 
         room_sizes                                           -- If these all conditions are satisfied we declare it a valid solution


-- Function to get best design among two designs based on the area covered by two designs
best_design :: [[Int]]->[[Int]]->[Int]->[[Int]]
best_design des1 des2 room_counts = do
    -- area of both ethe designs
    let area1 = total_area room_counts des1
    let area2 = total_area room_counts des2
    -- max area design should be considered
    if(area1>=area2) then
        des1
    else do
        des2

-- Function to get total area covered by all components
total_area :: [Int]->[[Int]]->Int
total_area room_counts room_sizes = do
    let area1 = ((room_area 0 room_sizes))*(room_counts!!0)
    let area2 = ((room_area 1 room_sizes))*(room_counts!!1)
    let area3 = ((room_area 2 room_sizes))*(room_counts!!2)
    let area4 = ((room_area 3 room_sizes))*(room_counts!!3)
    let area5 = ((room_area 4 room_sizes))*(room_counts!!4)
    let area6 = ((room_area 5 room_sizes))*(room_counts!!5)
    -- Sum Area of all components
    area1+area2+area3+area4+area5+area6


-- Function to get area of particular Component
room_area ::Int->[[Int]]->Int
room_area index room_sizes = if index >= 6 then 0 else do
     ((room_sizes!!index)!!0) * ((room_sizes!!index)!!1)



-- Print Result
print_result :: [[Int]] -> [Int] -> Int->ShowS
print_result sizes room_counts unused_space = showString "Bedroom: " . shows (room_counts!!0) . showString " (". shows ((sizes!!0)!!0) . showString "X" .  shows ((sizes!!0)!!1) . showString ") ;              " . showString "Hall: " . shows (room_counts!!1) . showString "(". shows ((sizes!!1)!!0) . showString "X" .  shows ((sizes!!1)!!1) . showString ");             " . showString "Kitchen: " . shows (room_counts!!2) . showString "(". shows ((sizes!!2)!!0) . showString "X" .  shows ((sizes!!2)!!1) . showString ");             " . showString "Bathroom: " . shows (room_counts!!3) . showString "(". shows ((sizes!!3)!!0) . showString "X" .  shows ((sizes!!3)!!1) . showString ");           " . showString "Balcony: " . shows (room_counts!!4) . showString "(". shows ((sizes!!4)!!0) . showString "X" .  shows ((sizes!!4)!!1) . showString ")" . shows(";             "). showString "Garden: " . shows (room_counts!!5) . showString "(". shows ((sizes!!5)!!0) . showString "X" .  shows ((sizes!!5)!!1) . showString ") ;             " . showString "Unused Space:" . shows (unused_space)



-- Initialization
design :: Int -> Int -> Int -> IO()
design size bedroom_count hall_count = do
    -- Initialize all the component counts
    let kitchen_count = ceiling ((fromIntegral bedroom_count)/(fromIntegral 3))
    let bathroom_count = bedroom_count+1
    let garden_count = 1
    let balcony_count = 1 
    -- Initialize minimum dimensions of each component
    let bedroom_size = [10, 10]
    let hall_size = [15, 10]
    let kitchen_size = [7, 5]
    let bathroom_size = [4, 5]
    let balcony_size = [5, 5]
    let garden_size = [10, 10]
    
    let room_counts = [bedroom_count, hall_count, kitchen_count, bathroom_count, garden_count, balcony_count]
    let room_sizes = [bedroom_size, hall_size, kitchen_size, bathroom_size, balcony_size, garden_size]
    let max_room_sizes = [[15,15],[20,15],[15,13],[8,9],[10,10],[20,20]]
    
    -- Get Final Size by calling Recursive function
    let final_sizes = checkRoom 0 room_counts room_sizes max_room_sizes size
    -- Unused space size -used space
    let unused_space = size - (total_area room_counts final_sizes)
    let rooms = ["Bedroom","Hall","Kitchen","Bathroom","Garden","Balcony","Unused Space"]
    
    -- Print Final Result
    if ( ((final_sizes!!0)!!0) > 0) then 
       print(print_result  final_sizes room_counts unused_space [])
    else
        print("No design possible for the given constraints")
        
        
